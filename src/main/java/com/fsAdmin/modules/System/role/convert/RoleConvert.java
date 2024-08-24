package com.fsAdmin.modules.System.role.convert;


import com.fsAdmin.modules.System.role.model.dto.CreateRoleDto;
import com.fsAdmin.modules.System.role.model.dto.UpdateRoleDto;
import com.fsAdmin.modules.System.role.model.entities.Role;
import com.fsAdmin.modules.System.role.model.vo.RoleVo;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleConvert {


    @InheritConfiguration
    Role createRoleDtoToEntity(CreateRoleDto createRoleDto);

    @InheritConfiguration
    Role updateRoleDtoToEntity(UpdateRoleDto updateRoleDto);

    @InheritConfiguration
    RoleVo entityToRoleVo(Role role);

    @InheritConfiguration
    List<RoleVo> entityListToRoleVoList(List<Role> roleList);
}
