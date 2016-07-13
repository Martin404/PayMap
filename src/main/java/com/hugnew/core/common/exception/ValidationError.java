package com.hugnew.core.common.exception;

/**
 * Created by Martin on 2016/7/01.
 */
public class ValidationError {

    private String objectName;

    private String fieldName;

    private String defaultMessage;

    public ValidationError() {
    }

    public ValidationError(String objectName, String fieldName, String defaultMessage) {

        this.objectName = objectName;

        this.fieldName = fieldName;

        this.defaultMessage = defaultMessage;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "objectName='" + objectName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", defaultMessage='" + defaultMessage + '\'' +
                '}';
    }
}
