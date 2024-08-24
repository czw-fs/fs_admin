package com.fsAdmin.modules.System.menu.controller;


import com.fsAdmin.modules.System.menu.model.dto.CreateMenuDto;
import com.fsAdmin.modules.System.menu.model.dto.MenuSearchDto;
import com.fsAdmin.modules.System.menu.model.dto.UpdateMenuDto;
import com.fsAdmin.modules.System.menu.model.vo.MenuVo;
import com.fsAdmin.modules.System.menu.model.vo.RouteVO;
import com.fsAdmin.modules.System.menu.service.MenuService;
import com.fsAdmin.modules.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Validated
public class MenuController {

    private final MenuService menuService;

    /**
     * 获取用户权限（路由）
     */
    @GetMapping("/routes")
    public Result<List<RouteVO>> getRoutes() {
        List<RouteVO> routeVOList = menuService.getRoutes();
        return Result.success(routeVOList);
    }

    /**
     * 新增菜单
     * @param menuDto
     * @return
     */
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Validated CreateMenuDto menuDto) {
        menuService.create(menuDto);
        return Result.success();
    }


    /**
     * 更新菜单
     * @param menuDto 菜单 DTO
     * @return {@link Result }<{@link Void }>
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody @Validated UpdateMenuDto menuDto) {
        menuService.update(menuDto);
        return Result.success();
    }

    /**
     * 查询菜单
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<MenuVo> getById(@PathVariable("id")Long id) {
        MenuVo menuVo = menuService.getOneById(id);
        return Result.success(menuVo);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        menuService.removeById(id);
        return Result.success();
    }

    /**
     * 获取菜单列表
     * @return
     */
    @GetMapping("/list")
    public Result<List<MenuVo>> getList(MenuSearchDto dto) {
        List<MenuVo> menuVoList = menuService.getList(dto);
        return Result.success(menuVoList);
    }

}
