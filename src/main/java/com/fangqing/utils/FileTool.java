package com.fangqing.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.diamond.client.Diamond;

/**
 * @功能 文件相关常量
 *
 * @author zhangfangqing
 * @date 2016年7月19日
 * @time 上午10:35:12
 */
public class FileTool {
	private static final Logger logger = LoggerFactory.getLogger(FileTool.class);

	public static String separator;
	public static String saveFilePath;// 下载中信文件，保存在工程根目录下
	public static String policyDownLoadPath ;// 投保下载文件路径
	public static String policyUpLoadPath ;// 投保差异、确认文件，上传路径 
	
	public static String FILE_SUFFIX_TXT = ".txt";

	public static String FILE_SUFFIX_CSV = ".csv";

	public static String POLICY_SUCCESS_CONFIRM_FILENAME = "121_policy_@_confirm.txt";
	public static String POLICY_FAIL_CONFIRM_FILENAME = "121_policy_@_confirm.txt";

	/*========================================================投保相关常量begin======================================================*/
	// 中信原投保文件名前缀
	private static String POLICY_ORIGIN_TOTAL_PREFIX ;
	private static String POLICY_DIFF_TOTAL_PREFIX ;
	private static String POLICY_CONFIRM_DETAIL_PREFIX ;
	public static String TOTAL = "_total";
	public static String DETAIL = "_detail";

	// 中信原投保数据校验不通过，返回错误文件
	public static String POLICY_ERROR_FILENAME = "policy_diff_error.txt";
	public static String POLICY_ERROR_CONTENT = "汇总数据和明细数据不匹配或者M1,M2比例不符合要求";
	public static String POLICY_SUCCESS_CONTENT = "ok";
	/*========================================================投保相关常量end======================================================*/
	
	
	
	/*========================================================理赔相关常量begin======================================================*/
	// 中信原理赔明细文件名前缀
	private static String CLAIM_ORIGIN_DETAIL_PREFIX ;
	public static String ORIGIN = "_origin";
	public static String DIFF = "_diff";
	public static String FINALL = "_final";
	public static String CLAIM_ERROR_FILENAME = "claim_diff_error.txt";
	/*========================================================理赔相关常量end======================================================*/
	
	
	static {
		try {
//			POLICY_ORIGIN_TOTAL_PREFIX = Diamond.getConfig("com.zhongan.badasset.policy.origin.total.prefix","DEFAULT_GROUP", 6000);
//			POLICY_DIFF_TOTAL_PREFIX = Diamond.getConfig("com.zhongan.badasset.policy.diff.total.prefix","DEFAULT_GROUP", 6000);
//			String confirmFileName = Diamond.getConfig("com.zhongan.badasset.policy.success.confirm.filename","DEFAULT_GROUP", 6000);
			separator = System.getProperty("file.separator");
			saveFilePath = System.getProperty("user.dir");
			policyDownLoadPath = Diamond.getConfig("com.zhongan.badasset.policy.download.path", "DEFAULT_GROUP", 6000);
			policyUpLoadPath = Diamond.getConfig("com.zhongan.badasset.policy.upload.path", "DEFAULT_GROUP", 6000);
			
			POLICY_ORIGIN_TOTAL_PREFIX = "121_orderData_";
			POLICY_DIFF_TOTAL_PREFIX = "121_policyDiff_";
			POLICY_CONFIRM_DETAIL_PREFIX = "121_policyConfirm_";
			
			CLAIM_ORIGIN_DETAIL_PREFIX = "121_claim_";
		} catch (Exception e) {
			logger.error("=========文件相关常量初始化异常： ", e);
		}
	}
	
	/**
	 * @功能 中信上传ftp服务器的原投保汇总文件名
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午5:46:42
	 */
	public static String getPolicyOriginTotalFileName() {
		return POLICY_ORIGIN_TOTAL_PREFIX + DateTool.formatYYYYMMDD(DateTool.getBeforeDay()) + TOTAL + FILE_SUFFIX_CSV;
	}

	/**
	 * @功能 中信上传ftp服务器的原投保汇总文件名
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午5:46:42
	 */
	public static String getPolicyOriginDetailFileName() {
		return POLICY_ORIGIN_TOTAL_PREFIX + DateTool.formatYYYYMMDD(DateTool.getBeforeDay()) + DETAIL + FILE_SUFFIX_CSV;
	}

	/**
	 * @功能 中信上传ftp服务器的差异汇总文件（包括可以投保和拒保的）
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午5:46:42
	 */
	public static String getPolicyDiffTotalFileName() {
		return POLICY_DIFF_TOTAL_PREFIX + DateTool.formatYYYYMMDD(new Date()) + TOTAL + FILE_SUFFIX_CSV;
	}

	/**
	 * @功能   中信上传ftp服务器的差异明细文件（包括可以投保和拒保的）
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午5:46:42
	 */
	public static String getPolicyDiffDetailFileName() {
		return POLICY_DIFF_TOTAL_PREFIX + DateTool.formatYYYYMMDD(new Date()) + DETAIL + FILE_SUFFIX_CSV;
	}
	
	
	
	/**
	 * @功能     最终返回给中信的已承保文件明细
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午5:46:42
	 */
	public static String getPolicyConfirmDetailFileName() {
		return POLICY_CONFIRM_DETAIL_PREFIX + DateTool.formatYYYYMMDD(new Date()) + DETAIL + FILE_SUFFIX_CSV;
	}

	//========================================理赔相关的，begin==============================================
	/**
	 * @功能 中信上传ftp服务器的原赔付明细文件名
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午5:46:42
	 */
	public static String getClaimOriginDetailFileName() {
		return CLAIM_ORIGIN_DETAIL_PREFIX + DateTool.formatYYYYMMDD(DateTool.getBeforeDay()) + ORIGIN + FILE_SUFFIX_CSV;
	}
	//========================================理赔相关的，end==============================================
	
	
	
	/**
	 * @功能 创建文件
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午6:56:35
	 */
	public static boolean createFile(File file) throws Exception {
		boolean flag = false;
		if (!file.exists()) {
			file.createNewFile();
			flag = true;
		}
		return flag;
	}

	/**
	 * @功能 往文件添加内容
	 *
	 * @author zhangfangqing
	 * @date 2016年7月21日
	 * @time 下午6:58:00
	 */
	public static boolean writeTxtFile(String content, File file) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			o.write(content.getBytes("GBK"));
			o.close();
			flag = true;
		} catch (Exception e) {
			logger.error("===========================================writeTxtFile异常：", e);
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}


	/**
	 * @功能   读取文件内容
	 *
	 * @author zhangfangqing 
	 * @throws IOException 
	 * @date 2016年7月21日 
	 * @time 下午8:57:15
	 */
	public static String readTxtFile(String filePath , String encoding) throws IOException {
		StringBuffer sb = new StringBuffer();
		String lineTxt = null ;
		InputStreamReader read = null ;
		BufferedReader bufferedReader = null ;
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 
				read = new InputStreamReader(new FileInputStream(file), encoding);
			    bufferedReader = new BufferedReader(read);
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb = sb.append(lineTxt);
				}
				read.close();
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("=========================读取文件内容出错异常：", e);
		}finally {
			if(read != null){
				read.close();
			}
			if(bufferedReader != null){
				bufferedReader.close();
			}
		}
		return sb.toString();
	}
	

	/**
	 * @功能       删除文件
	 *
	 * @author zhangfangqing 
	 * @date 2016年8月1日 
	 * @time 上午10:30:02
	 */
	public static void removeFile(String filePath, String suffix) throws IOException {
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File tmp = files[i];
			if (tmp.toString().endsWith(suffix)) {
				tmp.delete();
			}
		}
	}
	

}
