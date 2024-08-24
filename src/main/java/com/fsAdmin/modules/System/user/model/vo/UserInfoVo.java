package com.fsAdmin.modules.System.user.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 *  当前登录用户信息及权限
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {

    private Long id;

    private String username;

    private String avatar;

    private List<String> roleList;

    private Set<String> permissionList;


}
