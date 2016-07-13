package com.hugnew.core.common.exception;

/**
 * Created by Martin on 2016/7/01.
 */
public class SystemException extends BaseException {

    public SystemException(String message) {
        super(message, new Throwable(message));
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

}
