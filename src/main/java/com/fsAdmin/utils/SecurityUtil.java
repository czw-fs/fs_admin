package com.fsAdmin.utils;


import com.fsAdmin.config.security.login.dto.UserLoginInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private static final UserLoginInfo principal = (UserLoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return principal.getUserId();
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        return principal.getUsername();
    }

    /**
     * 获取用户名
     */
    public static String getSessionId() {
        return principal.getSessionId();
    }

    /**
     * 获取用户登录信息
     */
    public static UserLoginInfo getUserLoginInfo() {
        return principal;
    }

}
