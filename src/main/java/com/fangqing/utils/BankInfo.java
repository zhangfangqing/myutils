package com.fangqing.utils;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;


/**
 * Created by huangli on 2016/7/12.
 */
public class BankInfo implements Serializable{

	private static final long serialVersionUID = 5669665543222514857L;

	public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo();
        bankInfo.setAmount("aaaa");
        //验证Bean参数，并返回验证结果信息
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //验证Bean参数，并返回验证结果信息
        Set<ConstraintViolation<BankInfo>> validators = validator.validate(bankInfo);
        for (ConstraintViolation<BankInfo> constraintViolation : validators) {
            System.out.println(constraintViolation.getMessage());
        }
    }
    
    private String month;
    
    private String account;
    
    @Digits(fraction = 4, integer = 10, message = "金额格式错误")
    @DecimalMin("0.00")
    private String amount;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
