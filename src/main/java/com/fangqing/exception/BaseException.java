package com.fangqing.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * 类BaseException.java的实现描述：TODO 类实现描述
 * 
 * @author qiujianfeng 2016年6月7日 下午6:18:21
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
