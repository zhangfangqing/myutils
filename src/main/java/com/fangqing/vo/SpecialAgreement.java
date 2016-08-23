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
 * @time 下午4:35:07
 */
public class SpecialAgreement {
	
	private String flag ;
	
	private String title ;
	
	private String content ;

	public SpecialAgreement(){
		this.flag = PolicyPdfConstant.specialAgreementFlag;
		this.title = PolicyPdfConstant.specialAgreementTitle;
		this.content = PolicyPdfConstant.specialAgreementContent;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
