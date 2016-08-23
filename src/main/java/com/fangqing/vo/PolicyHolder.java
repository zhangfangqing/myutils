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
 * @time 下午4:22:48
 */
public class PolicyHolder {
	
	private String name ;
	
	private String certificateType ;
	
	private String certificateNo ;
	
	private String phoneNo ;
	
	private String contractAddrsss ;

	
	/**
	 * @param name
	 * @param certificateType
	 * @param certificateNo
	 * @param phoneNo
	 * @param contractAddrsss
	 */
	public PolicyHolder() {
		super();
		this.name = PolicyPdfConstant.zx_policy_person;
		this.certificateType = PolicyPdfConstant.zx_organ_certifi_type;
		this.certificateNo = PolicyPdfConstant.zx_id_number;
		this.phoneNo = PolicyPdfConstant.zx_phone;
		this.contractAddrsss = PolicyPdfConstant.zx_address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getContractAddrsss() {
		return contractAddrsss;
	}

	public void setContractAddrsss(String contractAddrsss) {
		this.contractAddrsss = contractAddrsss;
	}

}
