package com.fsAdmin.modules.System.role.model.dto;


import com.fsAdmin.modules.System.role.model.enums.RoleStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateRoleDto {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private RoleStatus status;

    /**
     * 备注
     */
    private String remark;

}
