package com.fsAdmin.modules.System.role.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsAdmin.modules.System.role.convert.RoleConvert;
import com.fsAdmin.modules.System.role.mapper.RoleMapper;
import com.fsAdmin.modules.System.role.model.dto.CreateRoleDto;
import com.fsAdmin.modules.System.role.model.dto.RoleSearchDto;
import com.fsAdmin.modules.System.role.model.dto.UpdateRoleDto;
import com.fsAdmin.modules.System.role.model.entities.Role;
import com.fsAdmin.modules.System.role.model.vo.RoleVo;
import com.fsAdmin.modules.System.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleConvert roleConvert;

    @Override
    public void create(CreateRoleDto createRoleDto) {
        Role role = roleConvert.createRoleDtoToEntity(createRoleDto);
        roleMapper.insert(role);
    }

    @Override
    public void update(UpdateRoleDto updateRoleDto) {
        Role role = roleConvert.updateRoleDtoToEntity(updateRoleDto);
        roleMapper.updateById(role);
    }

    @Override
    public RoleVo getOneById(Long id) {
        Role role = roleMapper.selectById(1);
        RoleVo roleVo = roleConvert.entityToRoleVo(role);
        return roleVo;
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteById(id);
    }

    @Override
    public List<RoleVo> getList(RoleSearchDto roleSearchDto) {
        List<Role> roleList = roleMapper.selectList(roleSearchDto);
        List<RoleVo> roleVoList = roleConvert.entityListToRoleVoList(roleList);
        return roleVoList;
    }
}
