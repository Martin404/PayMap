package com.hugnew.core.common.exception;

/**
 * 业务异常的自定义封装类
 * Created by Martin on 2016/7/01.
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message, new Throwable(message));
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
