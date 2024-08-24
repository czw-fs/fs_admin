package com.fsAdmin.modules.System.menu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fsAdmin.modules.System.menu.model.dto.CreateMenuDto;
import com.fsAdmin.modules.System.menu.model.dto.MenuSearchDto;
import com.fsAdmin.modules.System.menu.model.dto.UpdateMenuDto;
import com.fsAdmin.modules.System.menu.model.eneities.Menu;
import com.fsAdmin.modules.System.menu.model.vo.MenuVo;
import com.fsAdmin.modules.System.menu.model.vo.RouteVO;

import java.util.List;

public interface MenuService extends IService<Menu> {
    void create(CreateMenuDto menuDto);

    void update(UpdateMenuDto menuDto);

    MenuVo getOneById(Long id);

    List<MenuVo> getList(MenuSearchDto dto);

    List<RouteVO> getRoutes();
}
