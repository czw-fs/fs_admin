package com.fsAdmin.config.exeception;


import com.fsAdmin.modules.common.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalValidatedExceptionHandler {

    private static final String BAD_REQUEST_MSG = "客户端请求参数错误";

    // <1> 处理 form data方式调用接口校验失败抛出的异常
    @ExceptionHandler(BindException.class)
    public Result<Void> bindExceptionHandler(HttpServletResponse response,BindException e) {
        String errorMessage = getErrorMessage(response, e);
        return Result.error(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MSG, null);
    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> methodArgumentNotValidExceptionHandler(HttpServletResponse response,MethodArgumentNotValidException e) {
        String errorMessage = getErrorMessage(response, e);
        return Result.error(HttpStatus.BAD_REQUEST.value(), errorMessage, null);
    }

    public String getErrorMessage(HttpServletResponse response,BindException e){
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return String.join(",", errList);
    }


    // <3> 处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> constraintViolationExceptionHandler(HttpServletResponse response,ConstraintViolationException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> errList = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        return Result.error(HttpStatus.BAD_REQUEST.value(), String.join(",", errList), null);
    }
}
