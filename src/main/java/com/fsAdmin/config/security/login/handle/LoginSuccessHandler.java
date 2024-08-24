package com.fsAdmin.config.security.login.handle;


import com.fsAdmin.config.security.login.dto.UserLoginInfo;
import com.fsAdmin.modules.common.model.Result;
import com.fsAdmin.utils.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * 登录成功处理器
 */

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserLoginInfo currentUser)) {
            throw new RuntimeException("登陆认证成功后，返回的Object对象必须是：UserLoginInfo！");
        }

        //todo refreshToken

        currentUser.setSessionId(UUID.randomUUID().toString());
        String token = jwtUtil.createJwt(currentUser);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.print(new Gson().toJson(Result.success("登录成功",token)));
        writer.flush();
        writer.close();
    }
}
