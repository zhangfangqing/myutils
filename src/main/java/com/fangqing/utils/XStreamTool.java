package com.fangqing.utils;
import java.math.BigDecimal;
import java.util.Date;

import com.fangqing.vo.BasePolicyInfo;
import com.fangqing.vo.Data;
import com.fangqing.vo.Insurant;
import com.fangqing.vo.PolicyConfirmDetail;
import com.fangqing.vo.PolicyHolder;
import com.fangqing.vo.PolicyXmlParam;
import com.fangqing.vo.ProductClause;
import com.fangqing.vo.ProductClauses;
import com.fangqing.vo.SpecialAgreement;
import com.fangqing.vo.Specials;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @功能 TODO
 *
 * @author zhangfangqing 
 * @date 2016年8月15日 
 * @time 下午3:30:40
 */
public class XStreamTool {

	public static void main(String args[]) {
		System.out.println(XStreamTool.getPolicyToXml(null));
	}
	
	/**
	 * @功能 TODO
	 *
	 * @author zhangfangqing 
	 * @date 2016年8月19日 
	 * @time 上午9:57:14
	 */
	private static String getInsuredPeriod(PolicyConfirmDetail policyConfirmVo){
		return null;
	}
	
	/**
	 * @功能     获取测试demo数据
	 *
	 * @author zhangfangqing 
	 * @date 2016年8月15日 
	 * @time 下午7:48:39
	 */
	public static String getPolicyToXml(PolicyConfirmDetail policyConfirmVo) {
		if(policyConfirmVo == null){
			return null;
		}
		BigDecimal policyAmt = policyConfirmVo.getPolicyAmt(); //保额
		BigDecimal policyPremium = policyAmt.multiply(new BigDecimal("0.01")).setScale(2, BigDecimal.ROUND_HALF_UP);//保险费，保留2位小数
		
		Data data = new Data();
		data.setContractNo(policyConfirmVo.getAccountNo());
		data.setLoanName(policyConfirmVo.getAccountName());
		data.setLoanCertificateNo(policyConfirmVo.getIdno());
		data.setLoanAmount(policyAmt.toString());// 借款本金（小写）		
		data.setLoanAmountCN(NumberToCNTool.number2CNMontrayUnit(policyAmt));// 借款本金（大写）	
		
		BasePolicyInfo basePolicyInfo = new BasePolicyInfo();
		basePolicyInfo.setPolicyNo(policyConfirmVo.getPolicyNo());
		basePolicyInfo.setPolicyPremium(policyPremium.toString());//保险费（小写）
		basePolicyInfo.setPremiumCN(NumberToCNTool.number2CNMontrayUnit(policyPremium));//保险费（大写）
		basePolicyInfo.setSumInsured(policyAmt.toString());//保险金额(小写)
		basePolicyInfo.setSumInsuredCN(NumberToCNTool.number2CNMontrayUnit(policyAmt));//保险金额(大写)
		basePolicyInfo.setInsuredPeriod(getInsuredPeriod(policyConfirmVo));// 保险期限
		basePolicyInfo.setIssueDate(DateTool.formatYYYYMMDD(new Date()));// 盖章当天
		basePolicyInfo.setPayWay(DateTool.formatYYYYMMDD(new Date()));// 缴费方式及日期
		basePolicyInfo.setData(data);
		
		Specials specials = new Specials(new SpecialAgreement());
		ProductClauses productClauses = new ProductClauses(new ProductClause());
		
		PolicyXmlParam reqVo = new PolicyXmlParam(basePolicyInfo,new PolicyHolder(),new Insurant(),specials,productClauses);
		return XStreamTool.policyToXml(reqVo);
	}
	
	/**
	 * @功能 TODO
	 *
	 * @author zhangfangqing 
	 * @date 2016年8月15日 
	 * @time 下午7:48:39
	 */
	public static String policyToXml(PolicyXmlParam reqVo) {
		// 转换装配
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("policy", PolicyXmlParam.class);
		return xStream.toXML(reqVo);
	}
	
}