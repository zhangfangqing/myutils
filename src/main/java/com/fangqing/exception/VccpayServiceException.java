package com.fangqing.exception;


/**
 * @功能   业务异常
 *
 * @author zhangfangqing 
 * @date 2016年8月2日 
 * @time 下午2:24:05
 */
public class VccpayServiceException extends BaseException {

    private static final long serialVersionUID = -2947809878261298261L;
    /**
     * 错误码
     */
    private String            code;
    /**
     * 错误信息
     */
    private String            message;

    public VccpayServiceException(String message) {
        super(message);
        this.message = message;
    }

    public VccpayServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public VccpayServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public VccpayServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
