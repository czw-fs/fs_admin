package com.fsAdmin.config.security.login.handle;


import com.fsAdmin.modules.common.model.Result;
import com.fsAdmin.utils.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 认证失败处理器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final JwtUtil jwtUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final String token = request.getHeader("token");

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = response.getWriter();
        String msg = null;

        //token为空
        if (!StringUtils.hasLength(token)) {
            msg = "token为空";
            log.error(msg);
            writer.write(new Gson().toJson(Result.error(HttpStatus.UNAUTHORIZED.value(), msg)));
        }

        //非法token，无法解析
        Date tokenExpiredTime = null;
        if(msg == null){
            try {
                tokenExpiredTime = jwtUtil.getTokenExpiredTime(token);
                msg = "token无效";
            } catch (Exception e) {
                writer.write(new Gson().toJson(Result.error(HttpStatus.UNAUTHORIZED.value(), msg)));
            }
        }

        //过期token
        if(!jwtUtil.validToken(token) && msg == null){
            msg = "token已在 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tokenExpiredTime) + "过期，请重新登录";
            log.error(msg);
            writer.write(new Gson().toJson(Result.error(HttpStatus.UNAUTHORIZED.value(), msg)));
        }

        if(msg == null){
            writer.print(new Gson().toJson(Result.error(HttpStatus.UNAUTHORIZED.value(), "登录失败，请检查用户名或密码")));
        }

        writer.flush();
        writer.close();
    }
}