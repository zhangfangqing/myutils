package com.fangqing.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @功能 CSV解析工具
 *
 * @author zhangfangqing
 * @date 2016年7月6日
 * @time 下午3:12:06
 */
public class CsvTool {
	
	private static final String reg = "\\G(?:^|,)(?:\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))";
	
	private static final Pattern mainPattern = Pattern.compile(reg); // 行匹配
	
	private static final Pattern quotoPattern = Pattern.compile("\"\""); // 列匹配
	
	private final static Logger logger = LoggerFactory.getLogger(CsvTool.class);

	private final static String FILE_DATE = "logDate"; // 命名约定：DTO属性，存储所属文件日期

	public static <T> CsvParseResult<T> readCsvFile(String file, String encoding, Class<T> c, String[] fields,
			Map<Integer, String> fmMap, int skip, Date logDate) {
		return readCsvFile(file, encoding, c, fields, fmMap, skip, logDate, null);
	}

	/**
	 * @param file
	 *            待解析文件
	 * @param encoding
	 *            编码格式
	 * @param c
	 *            解析数据对应封装对象
	 * @param fields
	 *            封装对象对应的字段
	 * @param fmMap
	 *            key 针对fields的下标索引 value 转化格式
	 * @param skip
	 *            跳过指定行数，只能从第0行开始
	 * @param logDate
	 *            解析报文时，给每个对象的logDate属性自动赋值
	 * @return CsvParseResult
	 */
	public static <T> CsvParseResult<T> readCsvFile(String file, String encoding, Class<T> c, String[] fields,
			Map<Integer, String> fmMap, int skip, Date logDate, LineTransfter lineTransfter) {
		CsvParseResult<T> result = new CsvParseResult<T>();
		BufferedReader br = null;
		Matcher matcherMain = mainPattern.matcher("");
		Matcher matcherQuoto = quotoPattern.matcher("");

		String strLine = null; // 行数据
		String str = null; // 列数据
		List<T> listTemp = new ArrayList<T>(); // 结果集
		int rownum = 0; // 记录行号
		int column = 0;
		boolean parseSuccessFlag = true;
		String errorDesc = null; // 记录解析过程的错误信息，只保留最后一次错误。
		String allErrorDesc = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			// 跳过指定行数
			for (int i = 0; i < skip; i++) {
				strLine = br.readLine();
				if (i == 0) {
					// 只有需要跳行读取数据的才会有第一行的信息返回
					result.setFirstRow(strLine);
				}
				rownum++;
			}

			while ((strLine = br.readLine()) != null) {
				column = 0;
				if ("".equals(strLine.trim())) {
					continue;
				}
				rownum++;
				result.setRow(rownum);
				if (lineTransfter != null) {
					strLine = lineTransfter.transfter(strLine);
				}
				T obj = c.newInstance();
				if (obj instanceof IRow) {
					((IRow) obj).setRownum(rownum);
				}
				matcherMain.reset(strLine);
				for (int i = 0; i < fields.length; i++) {
					column++;
					if (fields[i] == null) {
						matcherMain.find();
						continue;
					}

					if (matcherMain.find()) {
						if (matcherMain.start(2) >= 0) {
							str = matcherMain.group(2);
						} else {
							str = matcherQuoto.reset(matcherMain.group(1)).replaceAll("\"");
						}
						if (str != null && !"".equals(str)) {
							try {
								String fm = (fmMap == null ? null : fmMap.get(i));
								Field f = null;
								try {
									f = c.getDeclaredField(fields[i]);
								} catch (NoSuchFieldException e) {
									// dto可能采用继承结构，目前先考虑一层继承关系
									f = c.getSuperclass().getDeclaredField(fields[i]);
								}
								f.setAccessible(true);
								f.set(obj, ReflectTool.changeTypeValue(str, f.getType(), fm));
								f.setAccessible(false);
							} catch (Exception e) {
								parseSuccessFlag = false;
								errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + e.toString();
								logger.error(errorDesc, e);
								logger.error(strLine);
								break;
							}
						}
					} else {
						parseSuccessFlag = false;
						errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + fields[i] + "在该行不存在";
						logger.warn(errorDesc);
						logger.warn(strLine);
						break;
					}
				} // end for(int i = 0; i < fields.length; i ++)

				if (parseSuccessFlag) {
					if (logDate != null) {
						setLogDate(c, obj, logDate);
					}
					listTemp.add(obj);
				} else {
					// 还原，便于下一次判断
					parseSuccessFlag = true;
					allErrorDesc += (errorDesc + " ");
				}
			} // end while((strLine = br.readLine()) != null)

			result.setTotal(rownum);

			if ((StringUtil.isNotBlank(allErrorDesc) || NullTool.isEmpty(listTemp)) && skip != rownum) {
				result.setSuccess(false);
				result.setErrorDesc(allErrorDesc);
			} else {
				result.setSuccess(true);
				result.setResultList(listTemp);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorDesc("列解析失败 " + e.toString());
			logger.error("========================列解析失败 或者文件找不到。。。。。。" );
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return result;
	}

	/**
	 * 解析指定格式csv文件（首行汇总数据+明细记录，格式参考翼支付T+1交易对账文件）
	 * 
	 * @param file
	 *            待解析文件
	 * @param encoding
	 *            编码格式
	 * @param c
	 *            解析数据对应封装对象
	 * @param firstLineFields
	 *            首行汇总记录封装对象对应的字段
	 * @param detailLineFields
	 *            明细记录封装对象对应的字段
	 * @param firstFMMap
	 *            首行汇总记录解析时字段格式转换
	 * @param detailFMMap
	 *            明细记录解析时字段格式转换
	 * @param logDate
	 *            解析报文时，给每个对象的logDate属性自动赋值
	 * @param lineTransfter
	 * @return
	 */
	public static <T> CsvParseResult<T> readCsvFileInAll(String file, String encoding, Class<T> c,
			String[] firstLineFields, String[] detailLineFields, Map<Integer, String> firstFMMap,
			Map<Integer, String> detailFMMap, Date logDate, LineTransfter lineTransfter) {

		CsvParseResult<T> result = new CsvParseResult<T>();
		BufferedReader br = null;
		Matcher matcherMain = mainPattern.matcher("");
		Matcher matcherQuoto = quotoPattern.matcher("");

		String strLine = null; // 行数据
		String str = null; // 列数据
		List<T> listTemp = new ArrayList<T>(); // 结果集
		int rownum = 0; // 记录行号
		int column = 0;
		boolean parseSuccessFlag = true;
		String errorDesc = null; // 记录解析过程的错误信息，只保留最后一次错误。
		String allErrorDesc = "";
		String[] fields = null;// 封装对象对应的字段
		Map<Integer, String> fmMap = null;// 解析时字段格式转换
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));

			while ((strLine = br.readLine()) != null) {
				column = 0;
				if ("".equals(strLine.trim())) {
					continue;
				}
				if (rownum == 0) {// 首行汇总记录
					fields = firstLineFields;
					fmMap = firstFMMap;
				} else {// 明细记录
					fields = detailLineFields;
					fmMap = detailFMMap;
				}
				rownum++;
				result.setRow(rownum);
				if (lineTransfter != null) {
					strLine = lineTransfter.transfter(strLine);
				}
				T obj = c.newInstance();
				if (obj instanceof IRow) {
					((IRow) obj).setRownum(rownum);
				}
				matcherMain.reset(strLine);
				for (int i = 0; i < fields.length; i++) {
					column++;
					if (fields[i] == null) {
						matcherMain.find();
						continue;
					}

					if (matcherMain.find()) {
						if (matcherMain.start(2) >= 0) {
							str = matcherMain.group(2);
						} else {
							str = matcherQuoto.reset(matcherMain.group(1)).replaceAll("\"");
						}
						if (str != null && !"".equals(str)) {
							try {
								String fm = (fmMap == null ? null : fmMap.get(i));
								Field f = null;
								try {
									f = c.getDeclaredField(fields[i]);
								} catch (NoSuchFieldException e) {
									// dto可能采用继承结构，目前先考虑一层继承关系
									f = c.getSuperclass().getDeclaredField(fields[i]);
								}
								f.setAccessible(true);
								f.set(obj, ReflectTool.changeTypeValue(str, f.getType(), fm));
								f.setAccessible(false);
							} catch (Exception e) {
								parseSuccessFlag = false;
								errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + e.toString();
								logger.error(errorDesc, e);
								logger.error(strLine);
								break;
							}
						}
					} else {
						parseSuccessFlag = false;
						errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + fields[i] + "在该行不存在";
						logger.warn(errorDesc);
						logger.warn(strLine);
						break;
					}
				}

				if (parseSuccessFlag) {
					if (logDate != null) {
						setLogDate(c, obj, logDate);
					}
					listTemp.add(obj);
				} else {
					// 还原，便于下一次判断
					parseSuccessFlag = true;
					allErrorDesc += (errorDesc + " ");
				}
			}

			result.setTotal(rownum);

			if ((StringUtil.isNotBlank(allErrorDesc) || NullTool.isEmpty(listTemp))) {
				result.setSuccess(false);
				result.setErrorDesc(allErrorDesc);
			} else {
				result.setSuccess(true);
				result.setResultList(listTemp);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorDesc("列解析失败 " + e.toString());
			logger.error("列解析失败 " + e.toString());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return result;
	}

	/**
	 * csv文件解析 ，有limit的情况
	 * 
	 * @param file
	 *            待解析文件
	 * @param encoding
	 *            编码格式
	 * @param c
	 *            解析数据对应封装对象
	 * @param fields
	 *            封装对象对应的字段
	 * @param fmMap
	 *            key 针对fields的下标索引 value 转化格式
	 * @param skip
	 *            跳过指定行数，只能从第0行开始
	 * @param logDate
	 *            解析报文时，给每个对象的logDate属性自动赋值
	 * @return CsvParseResult
	 */
	public static <T> CsvParseResult<T> readCsvFile(String file, String encoding, Class<T> c, String[] fields,
			Map<Integer, String> fmMap, int skip, int limit, Date logDate, LineTransfter lineTransfter) {
		CsvParseResult<T> result = new CsvParseResult<T>();
		BufferedReader br = null;
		Matcher matcherMain = mainPattern.matcher("");
		Matcher matcherQuoto = quotoPattern.matcher("");

		String strLine = null; // 行数据
		String str = null; // 列数据
		List<T> listTemp = new ArrayList<T>(); // 结果集
		int rownum = 0; // 记录行号
		int column = 0;
		boolean parseSuccessFlag = true;
		String errorDesc = null; // 记录解析过程的错误信息，只保留最后一次错误。
		String allErrorDesc = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			// 跳过指定行数
			for (int i = 0; i < skip; i++) {
				br.readLine();
				rownum++;
			}

			while ((strLine = br.readLine()) != null) {
				column = 0;
				if ("".equals(strLine.trim())) {
					continue;
				}
				if (rownum >= limit) {
					break;
				}
				rownum++;
				result.setRow(rownum);
				if (lineTransfter != null) {
					strLine = lineTransfter.transfter(strLine);
				}
				T obj = c.newInstance();
				if (obj instanceof IRow) {
					((IRow) obj).setRownum(rownum);
				}
				matcherMain.reset(strLine);
				for (int i = 0; i < fields.length; i++) {
					column++;
					if (fields[i] == null) {
						matcherMain.find();
						continue;
					}

					if (matcherMain.find()) {
						if (matcherMain.start(2) >= 0) {
							str = matcherMain.group(2);
						} else {
							str = matcherQuoto.reset(matcherMain.group(1)).replaceAll("\"");
						}
						if (str != null && !"".equals(str)) {
							try {
								String fm = (fmMap == null ? null : fmMap.get(i));
								Field f = null;
								try {
									f = c.getDeclaredField(fields[i]);
								} catch (NoSuchFieldException e) {
									// dto可能采用继承结构，目前先考虑一层继承关系
									f = c.getSuperclass().getDeclaredField(fields[i]);
								}
								f.setAccessible(true);
								f.set(obj, ReflectTool.changeTypeValue(str, f.getType(), fm));
								f.setAccessible(false);
							} catch (Exception e) {
								parseSuccessFlag = false;
								errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + e.toString();
								logger.error(errorDesc, e);
								logger.error(strLine);
								break;
							}
						}
					} else {
						parseSuccessFlag = false;
						errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + fields[i] + "在该行不存在";
						logger.warn(errorDesc);
						logger.warn(strLine);
						break;
					}
				} // end for(int i = 0; i < fields.length; i ++)

				if (parseSuccessFlag) {
					if (logDate != null) {
						setLogDate(c, obj, logDate);
					}
					listTemp.add(obj);
				} else {
					// 还原，便于下一次判断
					parseSuccessFlag = true;
					allErrorDesc += (errorDesc + " ");
				}
			} // end while((strLine = br.readLine()) != null)

			result.setTotal(rownum);

			if ((StringUtil.isNotBlank(allErrorDesc) || NullTool.isEmpty(listTemp)) && skip != rownum) {
				result.setSuccess(false);
				result.setErrorDesc(allErrorDesc);
			} else {
				result.setSuccess(true);
				result.setResultList(listTemp);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorDesc("列解析失败 " + e.toString());
			logger.error("列解析失败 " + e.toString());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return result;
	}

	/**
	 * 按约定，给DTO添加报文日期
	 * 
	 * @param c
	 * @param obj
	 * @param date
	 */
	private static <T> void setLogDate(Class<T> c, T obj, Date date) {
		try {
			Field f = c.getDeclaredField(FILE_DATE);
			f.setAccessible(true);
			f.set(obj, date);
			f.setAccessible(false);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * @param file
	 *            待解析文件
	 * @param encoding
	 *            编码格式
	 * @param c
	 *            解析数据对应封装对象
	 * @param fields
	 *            封装对象对应的字段
	 * @param fmMap
	 *            key 针对fields的下标索引 value 转化格式
	 * @param skip
	 *            跳过指定行数，只能从第0行开始
	 * @return CsvParseResult
	 */
	public static <T> CsvParseResult<T> readCsvFile(String file, String encoding, Class<T> c, String[] fields,
			Map<Integer, String> fmMap, int skip) {
		return readCsvFile(file, encoding, c, fields, fmMap, skip, null);
	}

	/**
	 * @param file
	 *            待解析文件
	 * @param encoding
	 *            编码格式
	 * @param c
	 *            解析数据对应封装对象
	 * @param fields
	 *            封装对象对应的字段
	 * @param skip
	 *            跳过指定行数，只能从第0行开始
	 * @return CsvParseResult
	 */
	public static <T> CsvParseResult<T> readCsvFile(String file, String encoding, Class<T> c, String[] fields,
			int skip) {
		return readCsvFile(file, encoding, c, fields, null, skip);
	}

	/**
	 * 根据反射将记录写到输出流中
	 * 
	 * @param str
	 *            需要导出的列明
	 * @param list
	 *            记录
	 * @author 郁岩生
	 * @return
	 */
	public static InputStream createCSVFile(String[] str, List<?> list) {
		if (str == null || list == null) {
			throw new RuntimeException("param str list is must not null");
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedWriter csvFileOutputStream = null;
		PropertyUtilsBean bean = new PropertyUtilsBean();
		ByteArrayInputStream in = null;
		try {
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(out, "utf-8"), 1024);
			String value = null;
			// 写入文件内容
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < str.length; j++) {

					try {
						// 新增对扩展字段extraInfo 进行处理
						if (str[j].startsWith("extraInfo")) {
							value = bean.getProperty(list.get(i), "extraInfo") == null ? ""
									: bean.getProperty(list.get(i), "extraInfo").toString();
							if (!StringUtils.isEmpty(value) && str[j].indexOf(".") > 0) {
								JSONObject obj = JSONObject.parseObject(value);
								String key = str[j].substring(str[j].indexOf(".") + 1, str[j].length());
								value = obj.get(key) == null ? "" : obj.get(key).toString();
							}
						} else if (bean.getPropertyType(list.get(i), str[j]).newInstance() instanceof Date) {
							value = bean.getProperty(list.get(i), str[j]) == null ? ""
									: new SimpleDateFormat("yyyyMMddHHmmss")
											.format(bean.getProperty(list.get(i), str[j]));
						} else {
							value = bean.getProperty(list.get(i), str[j]) == null ? ""
									: bean.getProperty(list.get(i), str[j]).toString();
						}
					} catch (Exception e) {
						value = bean.getProperty(list.get(i), str[j]) == null ? ""
								: bean.getProperty(list.get(i), str[j]).toString();
					}
					csvFileOutputStream.write(value);
					if (str.length - 1 != j) {
						csvFileOutputStream.write(",");
					}
				}
				if (list.size() - 1 != i) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			logger.error(ToStringBuilder.reflectionToString(e));
		} finally {
			IOUtils.closeQuietly(csvFileOutputStream);
			IOUtils.closeQuietly(out);
		}
		return in;
	}

	/**
	 * 根据反射将记录写到输出流中
	 * 
	 * @param str
	 *            需要导出的列明
	 * @param list
	 *            记录
	 * @author 郁岩生
	 * @return
	 */
	public static InputStream createCSVFile(String[] str, List<?> list, Map<Integer, String> fmMap) {
		if (str == null || list == null) {
			throw new RuntimeException("param str list is must not null");
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedWriter csvFileOutputStream = null;
		PropertyUtilsBean bean = new PropertyUtilsBean();
		ByteArrayInputStream in = null;
		try {
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(out, "utf-8"), 1024);
			String value = null;
			// 写入文件内容
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < str.length; j++) {

					try {
						// 新增对扩展字段extraInfo 进行处理
						if (str[j].startsWith("extraInfo")) {
							value = bean.getProperty(list.get(i), "extraInfo") == null ? ""
									: bean.getProperty(list.get(i), "extraInfo").toString();
							if (!StringUtils.isEmpty(value) && str[j].indexOf(".") > 0) {
								JSONObject obj = JSONObject.parseObject(value);
								String key = str[j].substring(str[j].indexOf(".") + 1, str[j].length());
								value = obj.get(key) == null ? "" : obj.get(key).toString();
							}
						} else if (bean.getPropertyType(list.get(i), str[j]).newInstance() instanceof Date) {
							String fm = (fmMap == null ? null : fmMap.get(j));
							if (StringUtil.isNotBlank(fm)) {
								value = bean.getProperty(list.get(i), str[j]) == null ? ""
										: new SimpleDateFormat(fm).format(bean.getProperty(list.get(i), str[j]));
							} else {
								value = bean.getProperty(list.get(i), str[j]) == null ? ""
										: new SimpleDateFormat("yyyyMMddHHmmss")
												.format(bean.getProperty(list.get(i), str[j]));
							}

						} else {
							value = bean.getProperty(list.get(i), str[j]) == null ? ""
									: bean.getProperty(list.get(i), str[j]).toString();
						}
					} catch (Exception e) {
						value = bean.getProperty(list.get(i), str[j]) == null ? ""
								: bean.getProperty(list.get(i), str[j]).toString();
					}
					csvFileOutputStream.write(value);
					if (str.length - 1 != j) {
						csvFileOutputStream.write(",");
					}
				}
				if (list.size() - 1 != i) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			logger.error("============CSV解析创建异常" , e);
		} finally {
			IOUtils.closeQuietly(csvFileOutputStream);
			IOUtils.closeQuietly(out);
		}
		return in;
	}

	/**
	 * 根据反射将记录写到输出流中，首行直接输出指定内容，生成文件格式：首行指定内容+（第二行开始）明细记录内容
	 * 
	 * @param firstLine
	 *            首行内容
	 * @param str
	 *            需要导出的列名
	 * @param list
	 *            记录
	 * @author chenwentong
	 * @return
	 */
	public static InputStream createCSVFile(String firstLine, String[] str, List<?> list) {
		if (str == null || list == null) {
			throw new RuntimeException("param str list is must not null");
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedWriter csvFileOutputStream = null;
		PropertyUtilsBean bean = new PropertyUtilsBean();
		ByteArrayInputStream in = null;
		try {
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(out, "utf-8"), 1024);
			String value = null;

			if (!StringUtils.isEmpty(firstLine)) {// 输出首行内容
				csvFileOutputStream.write(firstLine);
				csvFileOutputStream.newLine();
			}
			// 写入明细记录内容
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < str.length; j++) {

					try {
						// 新增对扩展字段extraInfo 进行处理
						if (str[j].startsWith("extraInfo")) {
							value = bean.getProperty(list.get(i), "extraInfo") == null ? ""
									: bean.getProperty(list.get(i), "extraInfo").toString();
							if (!StringUtils.isEmpty(value) && str[j].indexOf(".") > 0) {
								JSONObject obj = JSONObject.parseObject(value);
								String key = str[j].substring(str[j].indexOf(".") + 1, str[j].length());
								value = obj.get(key) == null ? "" : obj.get(key).toString();
							}
						} else if (bean.getPropertyType(list.get(i), str[j]).newInstance() instanceof Date) {
							value = bean.getProperty(list.get(i), str[j]) == null ? ""
									: new SimpleDateFormat("yyyyMMddHHmmss")
											.format(bean.getProperty(list.get(i), str[j]));
						} else {
							value = bean.getProperty(list.get(i), str[j]) == null ? ""
									: bean.getProperty(list.get(i), str[j]).toString();
						}
					} catch (Exception e) {
						value = bean.getProperty(list.get(i), str[j]) == null ? ""
								: bean.getProperty(list.get(i), str[j]).toString();
					}
					value = getCsvValue(value);
					csvFileOutputStream.write(value);
					if (str.length - 1 != j) {
						csvFileOutputStream.write(",");
					}
				}
				if (list.size() - 1 != i) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			logger.error("============CSV解析创建异常" , e);
		} finally {
			IOUtils.closeQuietly(csvFileOutputStream);
		}
		return in;
	}

	public static String getCsvValue(String value) {
		value = value.replace('\n', ' ');
		value = value.replace('\r', ' ');

		if (!value.startsWith("\"") && !value.endsWith("\"")) {
			value = value.replace(',', ' ');
			value = value.replace('"', ' ');
		}

		return value;
	}

	/**
	 * 读取文件总条数
	 * 
	 * @param file
	 *            文件路径
	 * @param encoding
	 *            字符集编码
	 * @return
	 */
	public static Integer readCsvFileByTotal(InputStream inputData, String encoding, int skip) {
		Integer total = new Integer(0);
		// 行数据
		String strLine = null;
		// 记录行号
		int rownum = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputData, encoding));

			// 跳过指定行数
			for (int i = 0; i < skip; i++) {
				// 读取下一行
				strLine = br.readLine();
			}

			while ((strLine = br.readLine()) != null) {
				if ("".equals(strLine.trim())) {
					continue;
				}
				rownum++;
			}
		} catch (Exception e) {
			logger.error("列解析失败 。", e);
		} finally {
			if (br != null) {
				try {
					inputData.close();
					br.close();
					total = new Integer(rownum);
				} catch (IOException e) {
					logger.error("关闭流异常。", e);
				}
			}
		}

		return total;
	}

	/**
	 * @param file
	 *            待解析文件
	 * @param encoding
	 *            编码格式
	 * @param c
	 *            解析数据对应封装对象
	 * @param fields
	 *            封装对象对应的字段
	 * @param fmMap
	 *            key 针对fields的下标索引 value 转化格式
	 * @param skip
	 *            跳过指定行数，只能从第0行开始
	 * @param logDate
	 *            解析报文时，给每个对象的logDate属性自动赋值
	 * @param beginLine
	 *            下标开始长度
	 * @param endLine
	 *            下标结束长度
	 * @return CsvParseResult
	 */
	public static <T> CsvParseResult<T> readCsvFileByBetween(InputStream inputData, String encoding, Class<T> c,
			String[] fields, Map<Integer, String> fmMap, int skip, Date logDate, LineTransfter lineTransfter,
			int beginLine, int endLine) {
		if (beginLine <= 0) {
			logger.warn("开始下标小于0.");
			return null;
		}

		if (endLine <= 0) {
			logger.warn("结束下标小于0.");
			return null;
		}

		if (beginLine > endLine) {
			logger.warn("开始下标大于结束下标.");
		}

		CsvParseResult<T> result = new CsvParseResult<T>();
		BufferedReader br = null;
		Matcher matcherMain = mainPattern.matcher("");
		Matcher matcherQuoto = quotoPattern.matcher("");

		String strLine = null; // 行数据
		String str = null; // 列数据
		List<T> listTemp = new ArrayList<T>(); // 结果集
		int rownum = 0; // 记录行号
		int column = 0;
		boolean parseSuccessFlag = true;
		String errorDesc = null; // 记录解析过程的错误信息，只保留最后一次错误。
		String allErrorDesc = "";
		int lines = 0;

		try {
			br = new BufferedReader(new InputStreamReader(inputData, encoding));

			// 跳过指定行数
			for (int i = 0; i < skip; i++) {
				// 读取下一行
				strLine = br.readLine();
			}

			while ((strLine = br.readLine()) != null) {

				if ("".equals(strLine.trim())) {
					continue;
				}

				rownum++;
				if (rownum >= beginLine && rownum <= endLine) {
					lines++;
					result.setRow(rownum);
					if (lineTransfter != null) {
						strLine = lineTransfter.transfter(strLine);
					}
					T obj = c.newInstance();
					if (obj instanceof IRow) {
						((IRow) obj).setRownum(rownum);
					}

					matcherMain.reset(strLine);
					for (int i = 0; i < fields.length; i++) {
						column++;
						if (fields[i] == null) {
							matcherMain.find();
							continue;
						}

						if (matcherMain.find()) {
							if (matcherMain.start(2) >= 0) {
								str = matcherMain.group(2);
							} else {
								str = matcherQuoto.reset(matcherMain.group(1)).replaceAll("\"");
							}
							if (str != null && !"".equals(str)) {
								try {
									String fm = (fmMap == null ? null : fmMap.get(i));
									Field f = null;
									try {
										f = c.getDeclaredField(fields[i]);
									} catch (NoSuchFieldException e) {
										// dto可能采用继承结构，目前先考虑一层继承关系
										f = c.getSuperclass().getDeclaredField(fields[i]);
									}
									f.setAccessible(true);
									f.set(obj, ReflectTool.changeTypeValue(str, f.getType(), fm));
									f.setAccessible(false);
								} catch (Exception e) {
									parseSuccessFlag = false;
									errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + e.toString();
									logger.error(errorDesc, e);
									logger.error(strLine);
									break;
								}
							}
						} else {
							parseSuccessFlag = false;
							errorDesc = "第" + rownum + "行" + column + "列解析失败 ." + fields[i] + "在该行不存在";
							logger.warn(errorDesc);
							logger.warn(strLine);
							break;
						}
					}

					if (parseSuccessFlag) {
						if (logDate != null) {
							setLogDate(c, obj, logDate);
						}
						listTemp.add(obj);
					} else {
						// 还原，便于下一次判断
						parseSuccessFlag = true;
						allErrorDesc += (errorDesc + " ");
					}
				}

				result.setTotal(lines);

				if ((StringUtil.isNotBlank(allErrorDesc) || NullTool.isEmpty(listTemp)) && skip != rownum) {
					result.setSuccess(false);
					result.setErrorDesc(allErrorDesc);
				} else {
					result.setSuccess(true);
					result.setResultList(listTemp);
				}

				if (rownum > endLine) {
					break;
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorDesc("列解析失败 " + e.toString());
			logger.error("列解析失败 " + e.toString());
		} finally {
			if (br != null) {
				try {
					inputData.close();
					br.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return result;
	}
	
	
	/**
	 * 根据反射将记录写到输出流中
	 * 
	 * @param filePath  文件路径
	 * @param fields 列名
	 * @param list 记录
	 * @param fmMap 格式化
	 * 
	 * @author 郁岩生
	 * @return
	 */
    public static void createCSVFileByPath(String filePath , String[] fields,  String[] fieldsZh, List<?> list, Map<Integer, String> fmMap)throws Exception{
    	InputStream inputStream = null;
    	FileOutputStream fos = null ;
    	try {
            // 第一行 字段说明需要定义成模板 采用文件附加形式处理
            inputStream = CsvTool.createCSVFile(fields, list, fmMap);
            fos = new FileOutputStream(filePath);
            byte[] data = new byte[1024];
            int len = 0;
            fos.write(getFirstRowStr(fieldsZh).getBytes("UTF-8"));
            fos.write("\n".getBytes());
            while ((len = inputStream.read(data)) != -1) {
                fos.write(data, 0, len);
            }
            inputStream.close();
            fos.close();
		} catch (Exception e) {
			logger.error("==============================根据反射将记录写到输出流中异常：", e);
		}finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
    }

    /**
     * @功能 TODO
     *
     * @author zhangfangqing 
     * @date 2016年7月22日 
     * @time 下午5:57:47
     */
	public static String getFirstRowStr(String[] fields) {
		StringBuilder sb = new StringBuilder(200);
		for (String str : fields) {
			sb.append(str).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
