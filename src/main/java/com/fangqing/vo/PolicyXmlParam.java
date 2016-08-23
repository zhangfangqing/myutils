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
 * @date 2016年8月15日 
 * @time 下午7:41:45
 */
public class PolicyXmlParam {
	
	private String voName ;
	
	private BasePolicyInfo basePolicyInfo;
	
	private PolicyHolder policyHolder;
	
	private Insurant insurant;

	private Specials specials;
	
	private ProductClauses productClauses;

	
	/**
	 * @param voName
	 * @param basePolicyInfo
	 * @param policyHolder
	 * @param insurant
	 * @param specials
	 * @param productClauses
	 */
	public PolicyXmlParam(BasePolicyInfo basePolicyInfo, PolicyHolder policyHolder, Insurant insurant,
			Specials specials, ProductClauses productClauses) {
		super();
		this.voName = PolicyPdfConstant.voName;
		this.basePolicyInfo = basePolicyInfo;
		this.policyHolder = policyHolder;
		this.insurant = insurant;
		this.specials = specials;
		this.productClauses = productClauses;
	}

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public BasePolicyInfo getBasePolicyInfo() {
		return basePolicyInfo;
	}

	public void setBasePolicyInfo(BasePolicyInfo basePolicyInfo) {
		this.basePolicyInfo = basePolicyInfo;
	}

	public PolicyHolder getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(PolicyHolder policyHolder) {
		this.policyHolder = policyHolder;
	}

	public Insurant getInsurant() {
		return insurant;
	}

	public void setInsurant(Insurant insurant) {
		this.insurant = insurant;
	}

	public Specials getSpecials() {
		return specials;
	}

	public void setSpecials(Specials specials) {
		this.specials = specials;
	}

	public ProductClauses getProductClauses() {
		return productClauses;
	}

	public void setProductClauses(ProductClauses productClauses) {
		this.productClauses = productClauses;
	}
	
}
