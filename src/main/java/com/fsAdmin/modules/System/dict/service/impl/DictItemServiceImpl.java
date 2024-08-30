package com.fsAdmin.modules.System.dict.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsAdmin.modules.System.dict.convert.DictItemConvert;
import com.fsAdmin.modules.System.dict.mapper.DictItemMapper;
import com.fsAdmin.modules.System.dict.mapper.DictMapper;
import com.fsAdmin.modules.System.dict.model.dto.CreateDictItemDto;
import com.fsAdmin.modules.System.dict.model.dto.DictItemSearchDto;
import com.fsAdmin.modules.System.dict.model.dto.UpdateDictItemDto;
import com.fsAdmin.modules.System.dict.model.entities.DictItem;
import com.fsAdmin.modules.System.dict.model.vo.DictItemSelectVo;
import com.fsAdmin.modules.System.dict.model.vo.DictItemVo;
import com.fsAdmin.modules.System.dict.service.DictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {


    private final DictMapper dictMapper;
    private final DictItemConvert dictItemConvert;
    private final DictItemMapper dictItemMapper;

    @Override
    public List<DictItemSelectVo> getDictItemVoListByDictCode(String dictCode) {
        List<DictItemSelectVo> result = dictMapper.selectDictItemSelectVoList(dictCode);
        return result;
    }

    @Override
    public void create(CreateDictItemDto dto) {
        DictItem  dictItem = dictItemConvert.createDictItemDtoToEntity(dto);
        dictItemMapper.insert(dictItem);
    }

    @Override
    public void update(UpdateDictItemDto dto) {
        DictItem  dictItem = dictItemConvert.updateDictItemDtoToEntity(dto);
        dictItemMapper.updateById(dictItem);
    }

    @Override
    public DictItemVo getOneById(Long id) {
        DictItem dictItem = dictItemMapper.selectById(id);
        DictItemVo dictItemVo = dictItemConvert.entityToDictItemVo(dictItem);
        return dictItemVo;
    }

    @Override
    public List<DictItemVo> getList(DictItemSearchDto dto) {
        List<DictItem> dictItemList = dictItemMapper.getList(dto);
        List<DictItemVo> dictItemVoList = dictItemConvert.entityListToDictItemVoList(dictItemList);
        return dictItemVoList;
    }



}
