package com.fsAdmin.config.security.loginChain.filter;


import com.fsAdmin.config.security.dto.CustomUsernamePasswordAuthenticationToken;
import com.fsAdmin.config.security.dto.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 获取请求体中用户名和密码，创建SpringSecurity需要的AbstractAuthenticationToken对象，并交给springsecurity进行验证
 */
public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private UserLoginDto userLoginDto;

    public UsernameAuthenticationFilter(AntPathRequestMatcher pathRequestMatcher,
                                        AuthenticationManager authenticationManager,
                                        AuthenticationSuccessHandler authenticationSuccessHandler,
                                        AuthenticationFailureHandler authenticationFailureHandler) {
        super(pathRequestMatcher);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 提取请求体的数据
        UserLoginDto userLoginDto = null;
        try {
            userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException("用户名或密码解析异常",e);
        }

        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            throw new RuntimeException("用户名或密码不能为空");
        }

        System.out.println(username + "-" + password);

        CustomUsernamePasswordAuthenticationToken authenticationToken = new CustomUsernamePasswordAuthenticationToken();
        authenticationToken
                .setUsername(username)
                .setPassword(password)
        ;
        //创建好认证对象后使用AuthenticationManager调用认证方法
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
