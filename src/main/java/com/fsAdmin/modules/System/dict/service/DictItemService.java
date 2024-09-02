package com.fsAdmin.modules.System.dict.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fsAdmin.modules.System.dict.model.dto.CreateDictItemDto;
import com.fsAdmin.modules.System.dict.model.dto.DictItemSearchDto;
import com.fsAdmin.modules.System.dict.model.dto.UpdateDictItemDto;
import com.fsAdmin.modules.System.dict.model.entities.DictItem;
import com.fsAdmin.modules.System.dict.model.vo.DictItemSelectVo;
import com.fsAdmin.modules.System.dict.model.vo.DictItemVo;

import java.util.List;

public interface DictItemService extends IService<DictItem> {
    void create(CreateDictItemDto dto);

    void update(UpdateDictItemDto dto);

    DictItemVo getOneById(Long id);

    List<DictItemVo> getList(DictItemSearchDto dto);

    List<DictItemSelectVo> getDictItemVoListByDictCode(String dictCode);

    List<DictItemVo> getDictItemListByDictId(Long dictId);
}
