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
 * @time 下午4:37:08
 */
public class ProductClause {
	
	private String clauseName ;
	
	private String linkUrl ;
	
	private String flag ;

	
	/**
	 * @param clauseName
	 * @param linkUrl
	 * @param flag
	 */
	public ProductClause() {
		this.clauseName = PolicyPdfConstant.productClauseClauseName;
		this.linkUrl = PolicyPdfConstant.productClauseLinkUrl;
		this.flag = PolicyPdfConstant.productClauseFlag;
	}

	public String getClauseName() {
		return clauseName;
	}

	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
