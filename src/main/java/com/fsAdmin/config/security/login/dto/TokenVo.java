package com.fsAdmin.config.security.login.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenVo {
    private String token;
    private String refreshToken;
}
