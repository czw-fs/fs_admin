package com.fsAdmin.config.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginInfo {

    private String sessionId; // 会话id，全局唯一
    private Long userId;
    private String username; // 用户名
}
