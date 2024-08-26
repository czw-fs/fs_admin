package com.fsAdmin.modules.System.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsAdmin.modules.System.user.model.dto.UserSearchDto;
import com.fsAdmin.modules.System.user.model.entities.User;
import com.fsAdmin.modules.System.user.model.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> getPage(@Param("searchDto") UserSearchDto searchDto, Page<UserVo> page);
}
