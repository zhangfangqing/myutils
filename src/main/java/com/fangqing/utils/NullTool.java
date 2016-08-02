package com.fangqing.utils;

import java.util.List;
import java.util.Map;

/**
 * @功能 TODO
 *
 * @author zhangfangqing 
 * @date 2016年7月7日 
 * @time 上午9:50:03
 */
public class NullTool {
	
	/**
	 * 字符串空判断
	 * @param arg
	 * @return true表示为null 或空串
	 */
	public static boolean isEmpty(String arg) {
		return arg == null || "".equals(arg.trim());
	}
	
	/**
	 * 集合空判断
	 * @param List
	 * @return true表示为空集合
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List arg) {
		return arg == null || arg.size() == 0;
	}
	
	/**
	 * 集合空判断
	 * @param Map
	 * @return true表示为空集合
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map arg) {
		return arg == null || arg.size() == 0;
	}
	
	/**
	 * 集合空判断
	 * @param Object
	 * @return true表示为空集合
	 */
	public static boolean isEmpty(Object[] arg) {
		return arg == null || arg.length == 0;
	}
	
	public static void main(String[] args) {
	}
}