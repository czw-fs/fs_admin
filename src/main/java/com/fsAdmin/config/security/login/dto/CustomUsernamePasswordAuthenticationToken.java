package com.fsAdmin.config.security.login.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class CustomUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = -8946671571319812197L;

    private String username;
    private String password;
    //当前登录用户信息
    private UserLoginInfo currentUser;
    //角色
    private Set<String> roleCodeSet;
    //权限
    private Set<String> permissionSet;

    public CustomUsernamePasswordAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);//权限
    }

    public CustomUsernamePasswordAuthenticationToken() {
        super(null); // 禁用权限
    }

    /**
     * 认证成功获取用户名密码
     * @return
     */
    @Override
    public Object getCredentials() {
        return isAuthenticated() ? null : password;
    }

    /**
     * 认证成功返回用户信息
     * @return
     */
    @Override
    public Object getPrincipal() {
        return isAuthenticated() ? currentUser : username;
    }
}
