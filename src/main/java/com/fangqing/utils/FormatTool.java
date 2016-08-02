/*
 * Copyright 2016 Zhongan.com All right reserved. This software is the
 * confidential and proprietary information of Zhongan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Zhongan.com.
 */
package com.fangqing.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @功能 格式化工具
 *
 * @author zhangfangqing
 * @date 2016年7月17日
 * @time 下午6:04:10
 */
public class FormatTool {

	// 投保汇总数据的所有列
	public static String[] POLICY_TOTAL_FIELDS = new String[] { "month", "addInsureNum", "m1OverdueCreditAmt","m1OverdueCreditNum", "m2OverdueCreditAmt", "m2OverdueCreditNum", "currentOverdueCreditAmt","currentOverdueCreditNum" };
	
	// 投保明细数据的所有列
	public static String[] POLICY_DETAIL_FIELDS = new String[] { "idno", "accountNo" , "accountName" , "overdueAmt" , "overdueType" ,"overdueDate"};

	// 返回投保差异明细
	public static String[] POLICY_DIFF_TOTAL_FIELDS = new String[] { "month", "diffFlag" , "allSuccessAmt" , "allSuccessNum" , "m1SuccessAmt" ,"m1SuccessNum" , "m2SuccessAmt" , "m2SuccessNum", "m1FailAmt" ,"m1FailNum" , "m2FailAmt" , "m2FailNum"};
	public static String[] POLICY_DIFF_DETAIL_FIELDS = new String[] { "idno", "accountName" , "accountNo" , "overdueAmt" , "overdueDate" ,"loanState" , "canPolicyFlag" , "diffReason"};
	// 返回投保差异明细
	public static String[] POLICY_DIFF_TOTAL_FIELDS_ZH = new String[] { "统计月份", "差异情况" , "当期承保逾期金额" , "当期承保账户数" , "当期承保M1总金额" ,"当期承保M1总笔数" , "当期承保M2总金额" , "当期承保M2总笔数", "当期拒保M1总金额" ,"当期拒保M1总笔数" , "当期拒保M2总金额" , "当期拒保M2总笔数"};
	public static String[] POLICY_DIFF_DETAIL_FIELDS_ZH = new String[] { "身份证", "姓名" , "账户号" , "逾期本金金额" , "逾期日期" ,"贷款状态" , "是否予以承保" , "不予承保原因"};
	
	
	// 返回众安已承保信息明细字段
	public static String[] POLICY_CONFIRM_DETAIL_FIELDS = new String[] { "policyNo" , "voucherNo" , "policyAmt" , "voucherBegintime" ,"voucherEndtime" , "idno" , "accountNo", "accountName"};
	// 返回众安已承保信息明细字段
	public static String[] POLICY_CONFIRM_DETAIL_FIELDS_ZH = new String[] { "保单号" , "保险凭证号" , "保额" , "凭证起期" ,"凭证止期" , "身份证" , "姓名", "账号"};
	
	
	// 理赔报案明细数据的所有列
	public static String[] CLAIM_DETAIL_FIELDS = new String[] { "idno", "accountNo" , "accountName" , "m4OverdueAmt" , "claimAmt" ,"m4OverdueDate"};

	public static String[] CLAIM_DIFF_DETAIL_FIELDS = new String[] { "idno", "accountName" , "accountNo" , "overdueAmt" , "overdueDate" ,"loanState" , "canPolicyFlag" , "diffReason"};
	public static String[] CLAIM_DIFF_DETAIL_FIELDS_ZH = new String[] { "idno", "accountName" , "accountNo" , "overdueAmt" , "overdueDate" ,"loanState" , "canPolicyFlag" , "diffReason"};
	/**
	 * @功能 格式化投保汇总数据
	 *
	 * @author zhangfangqing
	 * @date 2016年7月17日
	 * @time 下午6:10:33
	 */
	public static Map<Integer, String> getPolicyTotalFormatMap() {
		Map<Integer, String> formatMap = new HashMap<Integer, String>();
		formatMap.put(1, DateTool.DATE_YYYY_MM_DD);
		return formatMap;
	}

	/**
	 * @功能 格式化投保明细数据
	 *
	 * @author zhangfangqing
	 * @date 2016年7月17日
	 * @time 下午6:10:33
	 */
	public static Map<Integer, String> getPolicyDetailFormatMap() {
		Map<Integer, String> formatMap = new HashMap<Integer, String>();
		formatMap.put(1, DateTool.DATE_YYYY_MM_DD);
		formatMap.put(3, DateTool.DATE_YYYY_MM_DD_HHMMSS);
		return formatMap;
	}

}
