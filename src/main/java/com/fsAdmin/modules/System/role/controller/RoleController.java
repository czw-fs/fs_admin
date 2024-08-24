package com.fsAdmin.modules.System.role.controller;


import com.fsAdmin.modules.System.role.model.dto.CreateRoleDto;
import com.fsAdmin.modules.System.role.model.dto.RoleSearchDto;
import com.fsAdmin.modules.System.role.model.dto.UpdateRoleDto;
import com.fsAdmin.modules.System.role.model.vo.RoleVo;
import com.fsAdmin.modules.System.role.service.RoleService;
import com.fsAdmin.modules.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * 创建角色
     *
     * @param createRoleDto
     * @return
     */
    @PostMapping("/create")
    public Result<Void> create(@RequestBody CreateRoleDto createRoleDto) {
        roleService.create(createRoleDto);
        return Result.success();
    }

    /**
     * 更新角色
     *
     * @param updateRoleDto
     * @return
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody UpdateRoleDto updateRoleDto) {
        roleService.update(updateRoleDto);
        return Result.success();
    }

    /**
     * 根据获取角色
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<RoleVo> getById(@PathVariable("id") Long id) {
        RoleVo roleVo = roleService.getOneById(id);
        return Result.success(roleVo);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return Result.success();
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<RoleVo>> getList(RoleSearchDto roleSearchDto) {
        List<RoleVo> roleVoList = roleService.getList(roleSearchDto);
        return Result.success(roleVoList);
    }

}
