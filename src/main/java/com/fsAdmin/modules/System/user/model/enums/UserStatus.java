package com.fsAdmin.modules.System.user.model.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL("正常"),
    DISABLED("禁用")
    ;
    private final String name;

    UserStatus(String name){
        this.name = name;
    }
}
