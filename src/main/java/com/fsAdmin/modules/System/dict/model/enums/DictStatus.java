package com.fsAdmin.modules.System.dict.model.enums;

import lombok.Getter;

@Getter
public enum DictStatus {
    NORMAL("正常"),
    DISABLED("禁用"),
    ;

    private final String  name;

    DictStatus(String name) {
        this.name = name;
    }
}
