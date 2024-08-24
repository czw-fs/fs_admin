package com.fsAdmin.modules.System.dict.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateDictItemDto {
    /**
     * id
     */
    @Null(message = "新建时id应为空")
    private Long id;
    /**
     * 字典id
     */
    @NotNull(message = "字典id不能为空")
    private Long dictId;
    /**
     * 字典展示名称
     */
    @NotNull(message = "字典展示名称不能为空")
    @Size(min = 1,max = 20,message = "字典展示名称长度在1-20之间")
    private String name;
    /**
     * 字典值
     */
    @NotNull(message = "字典值不能为空")
    @Size(min = 1,max = 20,message = "字典值长度在1-20之间")
    private String value;
    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
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
