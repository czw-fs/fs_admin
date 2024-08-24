package com.fsAdmin.modules.System.menu.model.eneities;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fsAdmin.modules.System.menu.model.enums.MenuDisplay;
import com.fsAdmin.modules.System.menu.model.enums.MenuType;
import com.fsAdmin.modules.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends BaseEntity {

    @TableId
    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单类型
     */
    private MenuType type;
    /**
     * 路由名称
     */
    private String routerName;
    /**
     * 路由路径
     */
    private String routePath;
    /**
     * 组件路径
     */
    private String componentPath;
    /**
     * 【按钮】权限标识
     */
    private String permission;
    /**
     * 是否展示
     */
    private MenuDisplay display;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 跳转路径
     */
    private String redirect;
    /**
     * 路由参数
     */
    private String params;

}
