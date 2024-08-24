package com.fsAdmin.modules.System.menu.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 路由属性
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Meta {

    /**
     * 路由名称
     */
    private String title;

    /**
     * 路由图标
     */
    private String icon;

    /**
     * 是否展示
     */
    private Boolean hidden;

    /**
     * 是否固定在标签栏
     */
    private Boolean alwaysShow;

    /**
     * 路由参数
     */
    private Map<String, String> params;
}