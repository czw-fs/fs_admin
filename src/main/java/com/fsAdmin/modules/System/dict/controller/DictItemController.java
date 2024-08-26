package com.fsAdmin.modules.System.dict.controller;


import com.fsAdmin.modules.System.dict.model.dto.CreateDictItemDto;
import com.fsAdmin.modules.System.dict.model.dto.DictItemSearchDto;
import com.fsAdmin.modules.System.dict.model.dto.UpdateDictItemDto;
import com.fsAdmin.modules.System.dict.model.vo.DictItemVo;
import com.fsAdmin.modules.System.dict.service.DictItemService;
import com.fsAdmin.modules.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典子项
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dictItem")
public class DictItemController {

    private final DictItemService dictItemService;

    /**
     * 新增字典项
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Validated CreateDictItemDto dto) {
        dictItemService.create(dto);
        return Result.success();
    }

    /**
     * 修改字典项
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody UpdateDictItemDto dto) {
        dictItemService.update(dto);
        return Result.success();
    }

    /**
     * 查询字典项
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DictItemVo> getById(@PathVariable("id") Long id) {
        DictItemVo dictItemVo = dictItemService.getOneById(id);
        return Result.success(dictItemVo);
    }

    /**
     * 删除字典项
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        dictItemService.removeById(id);
        return Result.success();
    }

    /**
     * 查询字典项列表
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Result<List<DictItemVo>> getList(DictItemSearchDto dto) {
        List<DictItemVo> result = dictItemService.getList(dto);
        return Result.success(result);
    }
}
