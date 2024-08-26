package com.fsAdmin.config.exeception;


import com.fsAdmin.modules.common.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalWebExceptionHandle {


    /**
     *
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handAccessDeanin(AccessDeniedException e){
        return Result.error(500,"您没有此操作权限: " + e.getMessage());
    }

    /**
     * 匹配不到对应请求路径或获取请求参数格式错误
     *
     * @param ex 前任
     * @return {@link Result }<{@link String }>
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return Result.error(500,"匹配不到对应请求路径或获取请求参数格式错误: " + ex.getMessage());
    }


    /**
     * 兜底异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletResponse response, Exception e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return Result.error(500,"服务器异常" + e.getMessage());
    }

}
