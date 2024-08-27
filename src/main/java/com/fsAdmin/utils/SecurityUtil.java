package com.fsAdmin.utils;


import com.fsAdmin.config.security.dto.CustomUsernamePasswordAuthenticationToken;
import com.fsAdmin.config.security.dto.UserLoginInfo;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public class SecurityUtil {

    private static final CustomUsernamePasswordAuthenticationToken principal = (CustomUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return principal.getCurrentUser().getUserId();
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        return principal.getCurrentUser().getUsername();
    }

    /**
     * 获取用户当前会话id
     */
    public static String getSessionId() {
        return principal.getCurrentUser().getSessionId();
    }

    /**
     * 获取用户权限
     */
    public static Set<String> getPermissionSet() {
        return principal.getPermissionSet();
    }

    /**
     * 获取用户角色
     */
    public static Set<String> getRoleCodeSet() {
        return principal.getRoleCodeSet();
    }

    /**
     * 获取用户登录信息
     */
    public static UserLoginInfo getUserLoginInfo() {
        return principal.getCurrentUser();
    }

}
