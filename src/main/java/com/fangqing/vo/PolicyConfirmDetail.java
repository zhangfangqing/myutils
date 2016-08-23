package com.fangqing.vo;
import java.io.Serializable;


/**
 * 
 * 最终投保明细表（调投保接口成功的数据）
 * 
 **/
@SuppressWarnings("serial")
public class PolicyConfirmDetail implements Serializable {
	
	/****/
	private Long id;

	/**月份**/
	private String month;

	/**保单号**/
	private String policyNo;

	/**保险凭证号**/
	private String voucherNo;

	/**保额（默认为逾期金额）**/
	private java.math.BigDecimal policyAmt;

	/**凭证起期**/
	private String voucherBegintime;

	/**凭证止期（同保单止期）**/
	private String voucherEndtime;

	/**身份证号码**/
	private String idno;

	/**账户号**/
	private String accountNo;

	/**开户人姓名**/
	private String accountName;

	/**银行id**/
	private Integer bankId;

	/****/
	private java.util.Date gmtCreated;

	/****/
	private java.util.Date gmtModified;

	/**创建人**/
	private String creator;

	/**修改人**/
	private String modifier;

	/**标记删除**/
	private String isDeleted;

	/**是否投保成功，1成功；0失败**/
	private int isSuccess;

	/**失败原因**/
	private String failReason;


	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setMonth(String month){
		this.month = month;
	}

	public String getMonth(){
		return this.month;
	}

	public void setPolicyNo(String policyNo){
		this.policyNo = policyNo;
	}

	public String getPolicyNo(){
		return this.policyNo;
	}

	public void setVoucherNo(String voucherNo){
		this.voucherNo = voucherNo;
	}

	public String getVoucherNo(){
		return this.voucherNo;
	}

	public void setPolicyAmt(java.math.BigDecimal policyAmt){
		this.policyAmt = policyAmt;
	}

	public java.math.BigDecimal getPolicyAmt(){
		return this.policyAmt;
	}

	public void setVoucherBegintime(String voucherBegintime){
		this.voucherBegintime = voucherBegintime;
	}

	public String getVoucherBegintime(){
		return this.voucherBegintime;
	}

	public void setVoucherEndtime(String voucherEndtime){
		this.voucherEndtime = voucherEndtime;
	}

	public String getVoucherEndtime(){
		return this.voucherEndtime;
	}

	public void setIdno(String idno){
		this.idno = idno;
	}

	public String getIdno(){
		return this.idno;
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}

	public String getAccountNo(){
		return this.accountNo;
	}

	public void setAccountName(String accountName){
		this.accountName = accountName;
	}

	public String getAccountName(){
		return this.accountName;
	}

	public void setBankId(Integer bankId){
		this.bankId = bankId;
	}

	public Integer getBankId(){
		return this.bankId;
	}

	public void setGmtCreated(java.util.Date gmtCreated){
		this.gmtCreated = gmtCreated;
	}

	public java.util.Date getGmtCreated(){
		return this.gmtCreated;
	}

	public void setGmtModified(java.util.Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public java.util.Date getGmtModified(){
		return this.gmtModified;
	}

	public void setCreator(String creator){
		this.creator = creator;
	}

	public String getCreator(){
		return this.creator;
	}

	public void setModifier(String modifier){
		this.modifier = modifier;
	}

	public String getModifier(){
		return this.modifier;
	}

	public void setIsDeleted(String isDeleted){
		this.isDeleted = isDeleted;
	}

	public String getIsDeleted(){
		return this.isDeleted;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

}
