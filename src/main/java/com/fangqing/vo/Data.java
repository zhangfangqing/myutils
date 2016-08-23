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
 * @time 下午4:28:23
 */
public class Data {
	
	private String contractNo ;
	
	private String loanName ;
	
	private String loanCertificateType ;
	
	private String loanCertificateNo ;
	
	private String loanPhoneNo ;
	
	private String loanAmountCN ;
	
	private String loanAmount ;

	public Data(){
		this.loanCertificateType = PolicyPdfConstant.certificateType;
		this.loanPhoneNo = PolicyPdfConstant.loanPhoneNo;
	}
	
	/**
	 * @param contractNo
	 * @param loanName
	 * @param loanCertificateType
	 * @param loanCertificateNo
	 * @param loanPhoneNo
	 * @param loanAmountCN
	 * @param loanAmount
	 */
	public Data(String contractNo, String loanName,String loanCertificateNo,String loanAmountCN, String loanAmount) {
		super();
		this.contractNo = contractNo;
		this.loanName = loanName;
		this.loanCertificateType = PolicyPdfConstant.certificateType;
		this.loanCertificateNo = loanCertificateNo;
		this.loanPhoneNo = PolicyPdfConstant.loanPhoneNo;
		this.loanAmountCN = loanAmountCN;
		this.loanAmount = loanAmount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanCertificateType() {
		return loanCertificateType;
	}

	public void setLoanCertificateType(String loanCertificateType) {
		this.loanCertificateType = loanCertificateType;
	}

	public String getLoanCertificateNo() {
		return loanCertificateNo;
	}

	public void setLoanCertificateNo(String loanCertificateNo) {
		this.loanCertificateNo = loanCertificateNo;
	}

	public String getLoanPhoneNo() {
		return loanPhoneNo;
	}

	public void setLoanPhoneNo(String loanPhoneNo) {
		this.loanPhoneNo = loanPhoneNo;
	}

	public String getLoanAmountCN() {
		return loanAmountCN;
	}

	public void setLoanAmountCN(String loanAmountCN) {
		this.loanAmountCN = loanAmountCN;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
}
