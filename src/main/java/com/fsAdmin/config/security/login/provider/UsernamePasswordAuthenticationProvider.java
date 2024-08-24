package com.fsAdmin.config.security.login.provider;


import com.fsAdmin.config.security.login.dto.CustomUsernamePasswordAuthenticationToken;
import com.fsAdmin.config.security.login.dto.UserLoginInfo;
import com.fsAdmin.modules.System.menu.mapper.MenuMapper;
import com.fsAdmin.modules.System.role.mapper.RoleMapper;
import com.fsAdmin.modules.System.user.model.entities.User;
import com.fsAdmin.modules.System.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 使用用户名密码登录方式的认证逻辑
 */

@Component
@Slf4j
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;

    public UsernamePasswordAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomUsernamePasswordAuthenticationToken customToken = new CustomUsernamePasswordAuthenticationToken();
        //用户基本信息
        UserLoginInfo userLoginInfo = checkPassword(authentication);
        //角色
//        Set<String> roleSet = roleMapper.getRoleCodesByUserId(customToken.getCurrentUser().getUserId());
        //权限
//        Set<String> permissionSet = menuMapper.getPermissionByUserId(customToken.getCurrentUser().getUserId());

        customToken.setCurrentUser(userLoginInfo);
//                .setRoleList(roleSet)
//                .setPermissionList(permissionSet);

        customToken.setAuthenticated(true); // 认证通过，这里一定要设成true
        return customToken;
    }

    private UserLoginInfo checkPassword(Authentication authentication) {
        // 获取之前在UsernameAuthenticationFilter封装的对象
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = null;
        try {
            user = userService.selectUserByUsername(username);
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }

        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("用户名或密码不正确");
        }

        return new UserLoginInfo()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                ;
        /**
         * 这里security会将UserLoginInfo对象封装到Authentication对象中，并设置到当前线程的security的上下文中。接下载就可以使用SecurityContext了
         */
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /**
         *authentication 是否是 UsernameAuthentication 或其子类的实例。如果是，它会返回 true，否则返回 false。
         */
        return authentication.isAssignableFrom(CustomUsernamePasswordAuthenticationToken.class);
    }
}
