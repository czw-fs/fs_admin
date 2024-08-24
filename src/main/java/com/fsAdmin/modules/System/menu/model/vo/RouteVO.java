package com.fsAdmin.modules.System.menu.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteVO {
    /**
     * 路由名称
     */
    private String name;
    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 跳转链接
     */
    private String redirect;


    /**
     * 路由属性
     */
    private Meta meta;

    /**
     * 子路由
     */
    private List<RouteVO> children;
}