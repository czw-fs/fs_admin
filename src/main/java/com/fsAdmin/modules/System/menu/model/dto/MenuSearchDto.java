package com.fsAdmin.modules.System.menu.model.dto;


import com.fsAdmin.modules.System.menu.model.enums.MenuDisplay;
import com.fsAdmin.modules.System.menu.model.enums.MenuType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MenuSearchDto {

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单类型
     */
    private MenuType type;

    /**
     * 是否展示
     */
    private MenuDisplay display;
}
