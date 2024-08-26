package com.fsAdmin.modules.System.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsAdmin.modules.System.menu.mapper.MenuMapper;
import com.fsAdmin.modules.System.role.convert.RoleConvert;
import com.fsAdmin.modules.System.role.mapper.RoleMapper;
import com.fsAdmin.modules.System.role.model.entities.Role;
import com.fsAdmin.modules.System.user.convert.UserConvert;
import com.fsAdmin.modules.System.user.mapper.UserMapper;
import com.fsAdmin.modules.System.user.model.dto.CreateUserDto;
import com.fsAdmin.modules.System.user.model.dto.UpdateUserDto;
import com.fsAdmin.modules.System.user.model.dto.UserSearchDto;
import com.fsAdmin.modules.System.user.model.entities.User;
import com.fsAdmin.modules.System.user.model.vo.UserInfoVo;
import com.fsAdmin.modules.System.user.model.vo.UserVo;
import com.fsAdmin.modules.System.user.service.UserService;
import com.fsAdmin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final UserConvert userConvert;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;
    private final RoleConvert roleConvert;
    private final MenuMapper menuMapper;

    @Override
    public UserInfoVo getUserInfoVo() {
        Long userId = SecurityUtil.getUserId();

        //用户基本信息
        User user = userMapper.selectById(userId);
        UserInfoVo userInfoVo = userConvert.entityToUserInfoVo(user);

        //角色
        Set<Role> roleList = roleMapper.getRolesByUserId(userId);
        List<String> roleCodeList = roleList.stream().map(Role::getCode).toList();
        userInfoVo.setRoleList(roleCodeList);

        //权限
        Set<Long> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toSet());
        if(!CollectionUtils.isEmpty(roleIds)){
            Set<String> permissionList = menuMapper.getMenusByRoleIds(roleIds);
            userInfoVo.setPermissionList(permissionList);
        }
        return userInfoVo;
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).eq(User::getIsDeleted,false));
    }

    @Override
    public void createUser(CreateUserDto userDto) {
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);

        User user = userConvert.CreateUserDtoToEntity(userDto);

        userMapper.insert(user);
    }

    @Override
    public void updateUser(UpdateUserDto userDto) {
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);
        User user = userConvert.updateUserDtoToEntity(userDto);
        userMapper.updateById(user);
    }

    @Override
    public UserVo getOneById(Long id) {
        User user = userMapper.selectById(id);
        UserVo userVo = userConvert.entityToUserVo(user);
        return userVo;
    }

    @Override
    public Page<UserVo> selectPage(UserSearchDto searchDto) {
        Page<UserVo> page = new Page<>(searchDto.getPageNum(), searchDto.getPageSize());
        List<User> userList = userMapper.getPage(searchDto,page);

        List<UserVo> userVoList = userConvert.userListToUserVoList(userList);
        page.setRecords(userVoList);
        return page;
    }
}
