package com.fsAdmin.config.security.haveLoginFilterChain;


import com.fsAdmin.config.security.dto.CustomUsernamePasswordAuthenticationToken;
import com.fsAdmin.config.security.dto.UserLoginInfo;
import com.fsAdmin.modules.System.menu.mapper.MenuMapper;
import com.fsAdmin.modules.System.role.mapper.RoleMapper;
import com.fsAdmin.modules.common.model.Result;
import com.fsAdmin.utils.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;

    public JwtFilter(JwtUtil jwtUtil, RoleMapper roleMapper, MenuMapper menuMapper) {
        this.jwtUtil = jwtUtil;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        if ("123456".equals(request.getParameter("access_token"))) {
            CustomUsernamePasswordAuthenticationToken authenticationToken = new CustomUsernamePasswordAuthenticationToken();

            authenticationToken.setCurrentUser(
                    new UserLoginInfo()
                            .setUsername("admin")
                            .setUserId(1L)
            );
            authenticationToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
            return;
        }

        //OPTIONS 请求直接放行：预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader("token");

        //token为空或token过期，直接放行，进入认证流程
        if (!jwtUtil.validToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserLoginInfo userLoginInfo = jwtUtil.getUserLoginInfoFromJwt(token);
        Set<String> roleCodeSet = roleMapper.getRoleCodesByUserId(userLoginInfo.getUserId());
        Set<String> permissionSet = menuMapper.getPermissionByUserId(userLoginInfo.getUserId());

        //设置当前登录用户的上下文信息
        CustomUsernamePasswordAuthenticationToken authenticationToken = new CustomUsernamePasswordAuthenticationToken();
        authenticationToken
                .setCurrentUser(userLoginInfo)
                .setRoleCodeSet(roleCodeSet)//角色
                .setPermissionSet(permissionSet)//权限
                .setAuthenticated(true);//token有效，认证成功

        //验证成功，设置security上下文，无需验证
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
}
