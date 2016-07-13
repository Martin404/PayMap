package com.hugnew.core.common.exception;

/**
 * 数据库异常
 * Created by Martin on 2016/7/01.
 */
public class DBException extends BaseException {

    /**
     * @Fields serialVersionUID : default
     */
    private static final long serialVersionUID = 1L;

    public DBException(String message) {
        super(message, new Throwable());
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
