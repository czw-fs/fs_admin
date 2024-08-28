package com.fsAdmin.modules.System.user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsAdmin.modules.System.user.model.dto.CreateUserDto;
import com.fsAdmin.modules.System.user.model.dto.UpdateUserDto;
import com.fsAdmin.modules.System.user.model.dto.UserSearchDto;
import com.fsAdmin.modules.System.user.model.entities.User;
import com.fsAdmin.modules.System.user.model.vo.UserInfoVo;
import com.fsAdmin.modules.System.user.model.vo.UserVo;

public interface UserService extends IService<User> {
    User selectUserByUsername(String username);

    void createUser(CreateUserDto userDto);

    Page<UserVo> selectPage(UserSearchDto userDto);

    void updateUser(UpdateUserDto userDto);

    UserVo getOneById(Long id);

//    UserInfoVo getUserInfoVo();

    UserVo getCurrentUserInfo();
}
