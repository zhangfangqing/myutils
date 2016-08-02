package com.fangqing.utils;

/**
 * @功能       数据格式校验
 *
 * @author zhangfangqing 
 * @date 2016年7月28日 
 * @time 上午9:22:37
 */
public final class VerifyDataTool {
	
	/**
	 * 不能超过固定长度的数字
	 *
	 * @param data
	 *            可能只包含数字的字符串
	 * @return 是否只包含数字
	 */
	public static void isNumberForLeng(String data, int length) {
		isNumber(data);
		verifyLength(data, length);
	}
	
	/**
	 * 只含数字
	 *
	 * @param data
	 *            可能只包含数字的字符串
	 * @return 是否只包含数字
	 * @throws Exception 
	 */
	public static boolean isNumber(String data) {
		String expr = "^[0-9]+$";
		boolean verifyFlag = data.matches(expr);
		return verifyFlag;
	}
	

    /**
     * 长度验证
     *
     * @param data   源字符串
     * @param length 期望长度
     * @return 是否是期望长度
     */
    public static boolean verifyLength(String data, int length) {
    	boolean verifyFlag = (data != null && data.length() <= length);
		return verifyFlag;
    }

    /**
     * 只含字母
     *
     * @param data 可能只包含字母的字符串
     * @return 是否只包含字母
     */
    public static boolean isLetter(String data) {
        String expr = "^[A-Za-z]+$";
        boolean verifyFlag = data.matches(expr);
		return verifyFlag;
    }

    /**
     * 只含字母和数字
     *
     * @param data 可能只包含字母和数字的字符串
     * @return 是否只包含字母和数字
     */
    public static boolean isNumberLetter(String data) {
        String expr = "^[A-Za-z0-9]+$";
        boolean verifyFlag = data.matches(expr);
		return verifyFlag;
    }
    
    /**
     * 小数点位数
     *
     * @param data   可能包含小数点的字符串
     * @param rightLength 小数点后的长度
     * @return 是否小数点后有rightLength位数字
     */
    public static boolean isDianWeiShu(String data, int leftLength, int rightLength) {
    	// 校验小数点后有rightLength位数字
        String expr = "^[1-9][0-9]+\\.[0-9]{" + rightLength + "}$";
        boolean verifyFlag = data.matches(expr);
		// 校验小数点前有leftLength位数字
		String[] arr = data.split("\\.");
		verifyFlag = verifyLength(arr[0], leftLength);
		return verifyFlag;
    }
    
	/**
	 * 身份证号码验证
	 *
	 * @param data
	 *            可能是身份证号码的字符串
	 * @return 是否是身份证号码
	 */
	public static boolean isCard(String data) {
		String expr = "^[0-9]{17}[0-9xX]$";
		boolean verifyFlag = data.matches(expr);
		return verifyFlag;
	}
}