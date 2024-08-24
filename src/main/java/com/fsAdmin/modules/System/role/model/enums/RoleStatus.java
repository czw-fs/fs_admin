package com.fsAdmin.modules.System.role.model.enums;

import lombok.Getter;

@Getter
public enum RoleStatus {

    NORMAL("正常"),
    DISABLE("禁用")
    ;

    private final String name;

    RoleStatus(String name) {
        this.name = name;
    }
}
