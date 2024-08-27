package com.fsAdmin.config.security.loginChain.handle;


import com.fsAdmin.modules.common.model.Result;
import com.fsAdmin.utils.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录失败处理器
 */

@Component
@Slf4j
public class LoginFailHandler implements AuthenticationFailureHandler {


    private final JwtUtil jwtUtil;

    public LoginFailHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        log.error(exception.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter writer = response.getWriter();
        writer.print(new Gson().toJson(Result.error(exception.getMessage())));
        writer.flush();
        writer.close();
    }
}
