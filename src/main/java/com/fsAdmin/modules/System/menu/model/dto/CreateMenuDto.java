package com.fsAdmin.modules.System.menu.model.dto;


import com.fsAdmin.modules.System.menu.model.enums.MenuDisplay;
import com.fsAdmin.modules.System.menu.model.enums.MenuType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateMenuDto {
    /**
     * 父id
     */
    @NotNull(message = "父id不能为空")
    private Long parentId;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    /**
     * 菜单类型
     */
    @NotBlank(message = "菜单类型不能为空")
    private MenuType type;
    /**
     * 路由名称（Vue Router 中用于命名路由）
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
