package com.fsAdmin.modules.System.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsAdmin.modules.System.user.model.dto.CreateUserDto;
import com.fsAdmin.modules.System.user.model.dto.UpdateUserDto;
import com.fsAdmin.modules.System.user.model.dto.UserSearchDto;
import com.fsAdmin.modules.System.user.model.vo.UserInfoVo;
import com.fsAdmin.modules.System.user.model.vo.UserVo;
import com.fsAdmin.modules.System.user.service.UserService;
import com.fsAdmin.modules.common.model.Result;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/getCurrentUserInfo")
    public Result<UserVo> getCurrentUserInfo() {
        UserVo userVo = userService.getCurrentUserInfo();
        return Result.success(userVo);
    }

//    /**
//     * 获取当前登录用户信息，角色，权限
//     */
//    @GetMapping("/getCurLoginUserInfoVo")
//    public Result<UserInfoVo> getCurLoginUserInfoVo() {
//        UserInfoVo userInfoVo = userService.getUserInfoVo();
//        return Result.success(userInfoVo);
//    }

    /**
     * 新增用户
     *
     * @param userDto
     * @return
     */
    @PostMapping("/create")
    public Result<Void> createUser(@RequestBody @Validated CreateUserDto userDto) {
        userService.createUser(userDto);
        return Result.success();
    }

    /**
     * 修改用户
     *
     * @param userDto
     * @return
     */
    @PutMapping("/update")
    public Result<Void> updateUser(@RequestBody UpdateUserDto userDto) {
        userService.updateUser(userDto);
        return Result.success();
    }

    /**
     * 根据id查询用户
     */
    @GetMapping("/{id}")
    public Result<UserVo> getUserById(@PathVariable Long id) {
        UserVo userVo = userService.getOneById(id);
        return Result.success(userVo);
    }

    /**
     * 根据id删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUserById(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 分页查询用户
     *
     * @param userDto
     * @return
     */
    @GetMapping("/getUserPage")
    public Result<Page<UserVo>> getUserPage(@Validated UserSearchDto userDto) {
        Page<UserVo> userDtoPage = userService.selectPage(userDto);
        return Result.success(userDtoPage);
    }

    /**
     * 批量删除用户
     *
     * @return
     */
    @DeleteMapping("/deleteUserBatch")
    public Result<Void> deleteUserBatch(@RequestParam(value = "id",required = false) List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return Result.error("选项不能为空");
        }
        userService.deleteUserBatch(ids);
        return Result.success();
    }
}
