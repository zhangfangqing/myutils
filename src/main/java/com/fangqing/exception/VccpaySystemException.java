package com.fangqing.exception;

/**
 * 类VccpaySystemException.java的实现描述：系统异常
 * 
 * @author qiujianfeng 2016年6月7日 下午6:24:23
 */
public class VccpaySystemException extends BaseException {

    private static final long serialVersionUID = -2947809878261298271L;
    /**
     * 错误码
     */
    private String            code;
    /**
     * 错误信息
     */
    private String            message;

    public VccpaySystemException(String message) {
        super(message);
        this.message = message;
    }

    public VccpaySystemException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public VccpaySystemException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
