package com.fsAdmin.modules.System.dict.model.vo;

import com.fsAdmin.modules.System.dict.model.enums.DictStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DictVo {
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
    private DictStatus status;
    /**
     * 说明
     */
    private String remark;
}
