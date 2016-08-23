/*
 * Copyright 2016 Zhongan.com All right reserved. This software is the
 * confidential and proprietary information of Zhongan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Zhongan.com.
 */
package com.fangqing.vo;

import com.fangqing.constants.PolicyPdfConstant;

/**@功能 TODO
 *
 * @author zhangfangqing 
 * @date 2016年8月16日 
 * @time 下午4:22:36
 */
public class BasePolicyInfo {
	
	private String policyNo ;
	
	private String productName ;
	
	private String policyPremium ;
	
	private String premiumCN ;
	
	private String currency ;
	
	private String sumInsured ;
	
	private String sumInsuredCN ;
	
	private String policyRate ;
	
	private String waitDay ;
	
	private String insuredPeriod ;
	
	private String issueDate ;
	
	private String payWay ;
	
	private Data data ;
	
	private String disputeWay ;

	public BasePolicyInfo(){
		this.productName = PolicyPdfConstant.productName;
		this.currency = PolicyPdfConstant.currency;
		this.policyRate = PolicyPdfConstant.policyRate;
		this.waitDay = PolicyPdfConstant.waitDay;
		this.disputeWay = PolicyPdfConstant.disputeWay;
	}
	
	/**
	 * @param policyNo
	 * @param productName
	 * @param policyPremium
	 * @param premiumCN
	 * @param currency
	 * @param sumInsured
	 * @param sumInsuredCN
	 * @param policyRate
	 * @param waitDay
	 * @param insuredPeriod
	 * @param issueDate
	 * @param payWay
	 * @param data
	 * @param disputeWay
	 */
	public BasePolicyInfo(String policyNo, String policyPremium, String premiumCN, 
			String sumInsured, String sumInsuredCN,  String insuredPeriod,String issueDate,String payWay,Data data) {
		super();
		this.policyNo = policyNo;
		this.productName = PolicyPdfConstant.productName;
		this.policyPremium = policyPremium;
		this.premiumCN = premiumCN;
		this.currency = PolicyPdfConstant.currency;
		this.sumInsured = sumInsured;
		this.sumInsuredCN = sumInsuredCN;
		this.policyRate = PolicyPdfConstant.policyRate;
		this.waitDay = PolicyPdfConstant.waitDay;
		this.insuredPeriod = insuredPeriod;
		this.issueDate = issueDate;
		this.payWay = payWay;
		this.data = data;
		this.disputeWay = PolicyPdfConstant.disputeWay;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPolicyPremium() {
		return policyPremium;
	}

	public void setPolicyPremium(String policyPremium) {
		this.policyPremium = policyPremium;
	}

	public String getPremiumCN() {
		return premiumCN;
	}

	public void setPremiumCN(String premiumCN) {
		this.premiumCN = premiumCN;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getSumInsuredCN() {
		return sumInsuredCN;
	}

	public void setSumInsuredCN(String sumInsuredCN) {
		this.sumInsuredCN = sumInsuredCN;
	}

	public String getPolicyRate() {
		return policyRate;
	}

	public void setPolicyRate(String policyRate) {
		this.policyRate = policyRate;
	}

	public String getWaitDay() {
		return waitDay;
	}

	public void setWaitDay(String waitDay) {
		this.waitDay = waitDay;
	}

	public String getInsuredPeriod() {
		return insuredPeriod;
	}

	public void setInsuredPeriod(String insuredPeriod) {
		this.insuredPeriod = insuredPeriod;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getDisputeWay() {
		return disputeWay;
	}

	public void setDisputeWay(String disputeWay) {
		this.disputeWay = disputeWay;
	}
}
