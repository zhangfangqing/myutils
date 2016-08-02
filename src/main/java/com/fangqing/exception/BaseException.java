package com.fangqing.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * @功能  BaseException
 *
 * @author zhangfangqing 
 * @date 2016年8月2日 
 * @time 下午2:24:31
 */
public class BaseException extends NestedRuntimeException {

    private static final long serialVersionUID = -3738618644797028105L;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
