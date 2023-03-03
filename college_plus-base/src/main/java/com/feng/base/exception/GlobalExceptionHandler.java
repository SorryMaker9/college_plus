package com.feng.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 14:27
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //处理异常
    @ResponseBody //将信息返回Json
    @ExceptionHandler(CollegePlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//返回状态码500
    public RestErrorResponse doCollegePlusException(CollegePlusException e) {
        log.error("捕获异常:{}", e.getErrMessage());
        e.printStackTrace();
        String errMessage = e.getErrMessage();
        return new RestErrorResponse(errMessage);
    }

    //捕获不可预知异常 Exception
    @ResponseBody //将信息返回Json
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//返回状态码500
    public RestErrorResponse doException(Exception e) {
        log.error("捕获异常:{}", e.getMessage());
        e.printStackTrace();

        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }
    @ResponseBody //将信息返回Json
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//返回状态码500
    public RestErrorResponse doMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        //校验的错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //收集错误
        StringBuffer errors = new StringBuffer();
        fieldErrors.forEach(error->{
            errors.append(error.getDefaultMessage()).append(",");
        });
        return new RestErrorResponse(errors.toString());
    }
}