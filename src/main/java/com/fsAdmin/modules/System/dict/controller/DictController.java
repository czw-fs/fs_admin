package com.fsAdmin.modules.System.dict.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsAdmin.config.security.haveLoginFilterChain.checkPromission.CustomCheckPermission;
import com.fsAdmin.modules.System.dict.model.dto.CreateDictDto;
import com.fsAdmin.modules.System.dict.model.dto.DictSearchDto;
import com.fsAdmin.modules.System.dict.model.dto.UpdateDictDto;
import com.fsAdmin.modules.System.dict.model.vo.DictVo;
import com.fsAdmin.modules.System.dict.service.DictService;
import com.fsAdmin.modules.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/dict")
@Import({CustomCheckPermission.class})
public class DictController {

    private final DictService dictService;

    /**
     * 新增字典
     * @param dictDto
     * @return
     */
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Validated CreateDictDto dictDto) {
        dictService.create(dictDto);
        return Result.success();
    }

    /**
     * 更新字典
     * @param dictDto
     * @return
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody UpdateDictDto dictDto) {
        dictService.update(dictDto);
        return Result.success();
    }

    /**
     * 根据id获取字典
     * @param id
     * @return
     */
    @GetMapping("/{id}")
//    @PreAuthorize("@ss.hasPerm('abc')")
    public Result<DictVo> getDictById(@PathVariable("id")  Long id) {
        DictVo dictVo = dictService.getOneById(id);
        return Result.success(dictVo);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        dictService.removeById(id);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<DictVo>> page(DictSearchDto searchDto) {
        Page<DictVo> page = dictService.getPage(searchDto);
        return Result.success(page);
    }

    /**
     * 批量删除
     */
    @GetMapping("/deleteBatch")
    public Result<Page<DictVo>> deleteBatch(@RequestParam("id") List<Long> ids) {
        dictService.deleteBatch(ids);
        return Result.success();
    }
}
