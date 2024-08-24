package com.fsAdmin.modules.System.user.model.dto;


import com.fsAdmin.modules.System.user.model.enums.UserGender;
import com.fsAdmin.modules.System.user.model.enums.UserStatus;
import com.fsAdmin.modules.common.model.BasePage;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserSearchDto extends BasePage {
    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private UserGender gender;

    /**
     * 状态
     */
    private UserStatus status;
}
