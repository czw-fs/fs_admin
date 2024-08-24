package com.fsAdmin.config.security.access;

import jakarta.servlet.http.HttpServletRequest;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.PreAuthorizeAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 自定义权限验证处理器
 */
@Component
public class PermissionHandle implements AuthorizationManager<HttpServletRequest>{
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest request) {

        //验证成功
        return new PreAuthorizeAuthorizationManager().check(authentication, (MethodInvocation)request);
    }
}
