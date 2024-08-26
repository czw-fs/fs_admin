package com.fsAdmin.modules.System.menu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fsAdmin.modules.System.menu.model.dto.MenuSearchDto;
import com.fsAdmin.modules.System.menu.model.eneities.Menu;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Set;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getList(@Param("dto") MenuSearchDto dto);

    Set<String> getMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);

    Set<String> getPermissionByUserId(@Param("userId") Long userId);
}
