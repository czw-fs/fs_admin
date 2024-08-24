package com.fsAdmin.modules.System.dict.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DictItemVo {
    /**
     * id
     */
    private Long id;
    /**
     * 字典id
     */
    private Long dictId;
    /**
     * 字典展示名称
     */
    private String name;
    /**
     * 字典值
     */

    private String value;
    /**
     * 状态
     */
    private String status;
    /**
     * 排序：降序排
     */
    private String sort;
    /**
     * 说明
     */
    private String remark;
}
