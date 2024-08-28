package com.fsAdmin.config.exeception;


import com.fsAdmin.modules.common.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalWebExceptionHandle {


    /**
     *  无权限
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handAccessDeanin(HttpServletResponse response,AccessDeniedException e){
        String msg = "您没有此操作权限" + e.getMessage();
        log.error(msg,e);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        return Result.error(500,msg);
    }

    /**
     * 匹配不到对应请求路径或获取请求参数格式错误
     *
     * @param e
     * @return {@link Result }<{@link String }>
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleTypeMismatch(HttpServletResponse response,MethodArgumentTypeMismatchException e) {
        String msg = "请求参数的类型与控制器方法期望的参数类型不一致: " + e.getMessage();
        log.error(msg,e);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.error(500,msg);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Void> handleNotFound(HttpServletResponse response,NoHandlerFoundException e) {
        String msg = "匹配不到对应的控制单元路径: " + e.getMessage();
        log.error(msg,e);

        response.setStatus(HttpStatus.NOT_FOUND.value());
        return Result.error(500,msg);
    }

    /**
     * 兜底异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Void> exceptionHandler(HttpServletResponse response, Exception e) {
        String msg = "服务器异常：" + e.getMessage();
        log.error(msg,e);

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return Result.error(500,msg);
    }

}
