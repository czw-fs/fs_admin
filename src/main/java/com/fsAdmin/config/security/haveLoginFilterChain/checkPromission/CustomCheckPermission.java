package com.fsAdmin.config.security.haveLoginFilterChain.checkPromission;

import com.fsAdmin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component("ss")
@RequiredArgsConstructor
@Slf4j
public class CustomCheckPermission {

    public boolean hasPerm(String permissionCode) {
        Set<String> roleCodeSet = SecurityUtil.getRoleCodeSet();
        Set<String> permissionSet = SecurityUtil.getPermissionSet();

        if (!StringUtils.hasLength(permissionCode) || CollectionUtils.isEmpty(permissionSet) || CollectionUtils.isEmpty(roleCodeSet)) {
            log.error("用户id为 " + SecurityUtil.getUserId() + " 的用户" + "无角色和权限信息");
            return false;
        }

        boolean isAdmin = roleCodeSet.stream().anyMatch(item -> item.equals("admin"));
        if(isAdmin){
            return true;
        }

        return permissionSet.stream().anyMatch(item -> item.equals(permissionCode));
    }

}
