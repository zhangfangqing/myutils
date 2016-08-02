package com.fangqing.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能 TODO
 *
 * @author zhangfangqing 
 * @date 2016年7月7日 
 * @time 上午9:49:57
 */
public class ReflectTool {

    @SuppressWarnings("rawtypes")
    private static final Map<Class, Integer> typeMap = new HashMap<Class, Integer>();
    static {
        typeMap.put(String.class, 0);
        typeMap.put(Long.class, 1);
        typeMap.put(long.class, 1);
        typeMap.put(Integer.class, 2);
        typeMap.put(int.class, 2);
        typeMap.put(Date.class, 3);
        typeMap.put(BigDecimal.class, 4);
        typeMap.put(Boolean.class, 5);//添加boolean

    }

    //	@SuppressWarnings({ "rawtypes", "unchecked" })
    //	public static void objCopy1(Object src, Object dest) throws Exception {
    //		Class c1 = src.getClass();
    //		Class d1 = dest.getClass();
    //		Field[] fields = c1.getDeclaredFields();
    //		Field[] fields1 = d1.getDeclaredFields();
    //		Set<String> set = new HashSet<String>();
    //		for (Field temp : fields1) {
    //			set.add(temp.getName());
    //		}
    //
    //		for (Field field : fields) {
    //			String name = field.getName();
    //			if (!name.equals("serialVersionUID")) {
    //				if (!set.contains(name)) {
    //					continue;
    //				}
    //
    //				String temp = Character.toUpperCase(name.charAt(0))
    //						+ name.substring(1);
    //				Method m = c1.getMethod("get" + temp);
    //				Method m1 = d1.getMethod("set" + temp, field.getType());
    //				m1.invoke(dest, m.invoke(src, new Object[] {}));
    //			}
    //		}
    //	}

    @SuppressWarnings({ "rawtypes" })
    public static void objCopy(Object src, Object dest) throws Exception {
        Class c1 = src.getClass();
        Class d1 = dest.getClass();
        Field[] fields = c1.getDeclaredFields();
        Field[] fields1 = d1.getDeclaredFields();
        Map<String, Field> map = new HashMap<String, Field>();
        for (Field temp : fields) {
            map.put(temp.getName(), temp);
        }

        for (Field field : fields1) {
            String name = field.getName();
            Field temp = map.get(name);
            if (!name.equals("serialVersionUID")) {
                if (temp == null) {
                    continue;
                }
                field.setAccessible(true);
                temp.setAccessible(true);
                field.set(dest, temp.get(src));
                field.setAccessible(false);
                temp.setAccessible(false);
            }
        }
    }

    public static <T> T objCopy(Object src, Class<T> c) throws Exception {
        T dest = c.newInstance();
        objCopy(src, dest);
        return dest;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List changeListObj(List list, Class cs) throws Exception {
        List result = new ArrayList();
        for (Object c : list) {
            result.add(objCopy(c, cs));
        }
        return result;
    }

    /**
     * 把字符串根据数据类型，进行响应转化
     * 
     * @param arg0
     * @param cs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object changeTypeValue(String arg0, Class cs) throws Exception {
        return changeTypeValue(arg0, cs, null);
    }

    /**
     * 把字符串根据数据类型，进行响应转化
     * 
     * @param arg0
     * @param cs
     * @param fm 针对日期类型数据转化
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object changeTypeValue(String arg0, Class cs, String fm) throws Exception {
        Object obj = null;
        switch (typeMap.get(cs)) {
            case 0:
                obj = arg0;
                break;
            case 1:
                obj = new Long(arg0);
                break;
            case 2:
                obj = new Integer(arg0);
                break;
            case 3:
                obj = (fm == null ? DateTool.ignoreDate(arg0) : new SimpleDateFormat(fm).parse(arg0));
                break;
            case 4:
                obj = new BigDecimal(arg0);
                break;
            case 5:
                if (null != arg0) {//添加boolean类型判断
                    if (arg0.toString().toUpperCase().equals("TRUE") || arg0.equals("1")) {
                        obj = Boolean.TRUE;
                        break;
                    } else {
                        obj = Boolean.FALSE;
                        break;
                    }
                } else {
                    obj = Boolean.FALSE;
                    break;
                }
            default:
                break;
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {
    }
}