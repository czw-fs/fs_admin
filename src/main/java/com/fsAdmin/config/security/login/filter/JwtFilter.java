package com.fsAdmin.config.security.login.filter;


import com.fsAdmin.config.security.login.dto.CustomUsernamePasswordAuthenticationToken;
import com.fsAdmin.config.security.login.dto.UserLoginInfo;
import com.fsAdmin.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil, AuthenticationFailureHandler authenticationFailureHandler) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        //OPTIONS 请求直接放行：预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader("token");

        //token为空或token过期，直接放行，进入认证流程
        if(!StringUtils.hasLength(token) || !jwtUtil.isValidToken(token)){
            chain.doFilter(request, response);
            return;
        }

        UserLoginInfo userLoginInfo = jwtUtil.getUserLoginInfoFromJwt(token);

        CustomUsernamePasswordAuthenticationToken authenticationToken = new CustomUsernamePasswordAuthenticationToken();
        authenticationToken.setCurrentUser(userLoginInfo);
        authenticationToken.setAuthenticated(true);//token有效，认证成功

        //验证成功，设置security上下文，无需验证
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
}
