package com.fsAdmin.modules.System.role.model.dto;


import com.fsAdmin.modules.System.role.model.enums.RoleStatus;
import lombok.Data;

@Data
public class RoleSearchDto {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态
     */
    private RoleStatus status;

}
