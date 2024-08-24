package com.fsAdmin.modules.System.role.model.entities;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fsAdmin.modules.System.role.model.enums.RoleStatus;
import com.fsAdmin.modules.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2765133205408687577L;
    /**
     * id
     */
    @TableId
    private Long id;

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