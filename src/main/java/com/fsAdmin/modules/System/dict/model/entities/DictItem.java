package com.fsAdmin.modules.System.dict.model.entities;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fsAdmin.modules.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict_item")
public class DictItem extends BaseEntity {
    /**
     * id
     */
    @TableId
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
    private Integer sort;
    /**
     * 说明
     */
    private String remark;

}
