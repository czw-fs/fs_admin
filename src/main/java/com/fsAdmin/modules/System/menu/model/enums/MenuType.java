package com.fsAdmin.modules.System.menu.model.enums;

import lombok.Getter;

@Getter
public enum MenuType {
    CATALOG("目录"),
    MENU("菜单"),
    BUTTON("按钮"),
    LINK("链接")
    ;

    private final String name;

    MenuType(String name) {
        this.name = name;
    }
}
