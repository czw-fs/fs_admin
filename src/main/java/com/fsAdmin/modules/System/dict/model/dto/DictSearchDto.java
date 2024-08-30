package com.fsAdmin.modules.System.dict.model.dto;


import com.fsAdmin.modules.System.dict.model.enums.DictStatus;
import com.fsAdmin.modules.common.model.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 查询dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DictSearchDto extends BasePage {

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一编码
     */
    private String code;

    /**
     * 状态
     */
    private DictStatus dictStatus;
}
