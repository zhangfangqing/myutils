/*
 * Copyright 2016 Zhongan.com All right reserved. This software is the
 * confidential and proprietary information of Zhongan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Zhongan.com.
 */
package com.fangqing.vo;

/**@功能 TODO
 *
 * @author zhangfangqing 
 * @date 2016年8月16日 
 * @time 下午4:23:03
 */
public class Specials {
	
	private SpecialAgreement specialAgreement ;

	/**
	 * @param specialAgreement
	 */
	public Specials(SpecialAgreement specialAgreement) {
		super();
		this.specialAgreement = specialAgreement;
	}

	public SpecialAgreement getSpecialAgreement() {
		return specialAgreement;
	}

	public void setSpecialAgreement(SpecialAgreement specialAgreement) {
		this.specialAgreement = specialAgreement;
	}


}
