package com.fsAdmin.modules.System.dict.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsAdmin.modules.System.dict.convert.DictConvert;
import com.fsAdmin.modules.System.dict.mapper.DictItemMapper;
import com.fsAdmin.modules.System.dict.mapper.DictMapper;
import com.fsAdmin.modules.System.dict.model.dto.CreateDictDto;
import com.fsAdmin.modules.System.dict.model.dto.DictSearchDto;
import com.fsAdmin.modules.System.dict.model.dto.UpdateDictDto;
import com.fsAdmin.modules.System.dict.model.entities.Dict;
import com.fsAdmin.modules.System.dict.model.entities.DictItem;
import com.fsAdmin.modules.System.dict.model.vo.DictVo;
import com.fsAdmin.modules.System.dict.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final DictConvert dictConvert;
    private final DictMapper dictMapper;
    private final DictItemMapper dictItemMapper;

    @Override
    public void create(CreateDictDto dictDto) {
        Dict dict = dictConvert.createDictDToEntity(dictDto);
        dictMapper.insert(dict);
    }

    @Override
    public void update(UpdateDictDto dictDto) {
        Dict dict = dictConvert.updateDictDToEntity(dictDto);
        dictMapper.updateById(dict);
    }

    @Override
    public DictVo getOneById(Long id) {
        Dict dict = dictMapper.selectById(id);
        DictVo dictVo = dictConvert.dictToDictVo(dict);
        return dictVo;
    }

    @Override
    public Page<DictVo> getPage(DictSearchDto dto) {
        Page<DictVo> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        List<Dict> dictList = dictMapper.getPage(dto, page);

        List<DictVo> dictVoList = dictConvert.dictListToDictVoList(dictList);
        setHasChildren(dictVoList);

        page.setRecords(dictVoList);
        return page;
    }

    private void setHasChildren(List<DictVo> dictVoList) {
        if(CollectionUtils.isEmpty(dictVoList)){
            return;
        }
        dictVoList.forEach(this::accept);
    }

    private void accept(DictVo item) {
        Long count = dictItemMapper.selectCount(new LambdaQueryWrapper<DictItem>().eq(DictItem::getDictId, item.getId()));
        if (count == 0) {
            return;
        }
        item.setHasChildren(true);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        removeBatchByIds(ids);
    }


}
