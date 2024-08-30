package com.fsAdmin.modules.System.user.model.dto;


import com.fsAdmin.modules.System.user.model.enums.UserGender;
import com.fsAdmin.modules.System.user.model.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateUserDto {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private UserGender userGender;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态
     */
    private UserStatus userStatus;
}
