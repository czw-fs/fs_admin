package com.fsAdmin.modules.System.dict.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DictItemSearchDto {
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
}
