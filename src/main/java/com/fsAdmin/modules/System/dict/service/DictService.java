package com.fsAdmin.modules.System.dict.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsAdmin.modules.System.dict.model.dto.CreateDictDto;
import com.fsAdmin.modules.System.dict.model.dto.DictSearchDto;
import com.fsAdmin.modules.System.dict.model.dto.UpdateDictDto;
import com.fsAdmin.modules.System.dict.model.entities.Dict;
import com.fsAdmin.modules.System.dict.model.vo.DictVo;

public interface DictService extends IService<Dict> {
    void create(CreateDictDto dictDto);

    void update(UpdateDictDto dictDto);

    DictVo getOneById(Long id);

    Page<DictVo> getPage(DictSearchDto searchDto);
}
