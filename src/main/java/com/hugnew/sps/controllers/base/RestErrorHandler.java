package com.hugnew.sps.controllers.base;

import com.hugnew.core.common.exception.*;
import com.hugnew.core.common.model.json.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

/**
 * 控制层异常统一处理
 * Created by Martin on 2016/7/01.
 */
@ControllerAdvice
public class RestErrorHandler {

    private static Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public AjaxResult handleBindException(BindException exception) {
        AjaxResult result = AjaxResult.getError(ResultCode.ParamException);
        Set<ValidationError> errors = new HashSet<ValidationError>();
        for (FieldError er : exception.getFieldErrors()) {
            errors.add(new ValidationError(er.getObjectName(), er.getField(), er.getDefaultMessage()));
        }
        result.setData(errors);
        logger.warn("参数绑定错误:{}", exception.getObjectName());
        return result;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        AjaxResult result = AjaxResult.getError(ResultCode.ParamException);
        Set<ValidationError> errors = new HashSet<ValidationError>();
        for (FieldError er : exception.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationError(er.getObjectName(), er.getField(), er.getDefaultMessage()));
        }
        result.setData(errors);
        logger.warn("参数绑定错误:{}", exception.getBindingResult().getObjectName());
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public AjaxResult handleBusinessException(BusinessException exception) {
        AjaxResult result = AjaxResult.getError(ResultCode.BusinessException);
        result.setMessage(exception.getMessage());
        logger.warn("业务错误:{}", exception.getMessage());
        return result;
    }

    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public AjaxResult handleSystemException(SystemException exception) {
        AjaxResult result = AjaxResult.getError(ResultCode.SystemException);
        result.setMessage("系统错误");
        logger.error("系统错误:{}", exception);
        return result;
    }

    @ExceptionHandler(DBException.class)
    @ResponseBody
    public AjaxResult handleDBException(DBException exception) {
        AjaxResult result = AjaxResult.getError(ResultCode.DBException);
        result.setMessage("数据库错误");
        logger.error("数据库错误:{}", exception);
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult handleException(Exception exception) {
        AjaxResult result = AjaxResult.getError(ResultCode.UnknownException);
        result.setMessage("服务器错误");
        logger.error("服务器错误:{}", exception);
        return result;
    }
}
