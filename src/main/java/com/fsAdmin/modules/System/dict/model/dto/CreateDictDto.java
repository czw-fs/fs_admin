package com.fsAdmin.modules.System.dict.model.dto;

import com.fsAdmin.modules.System.dict.model.enums.DictStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateDictDto {
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;
    /**
     * 唯一编码
     */
    @NotBlank(message = "唯一编码不能为空")
    private String code;
    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private DictStatus status;
    /**
     * 说明
     */
    private String remark;
}
