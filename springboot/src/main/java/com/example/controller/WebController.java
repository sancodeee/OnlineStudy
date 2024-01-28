package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 基础前端接口
 *
 * @author wangsen
 * @date 2024/01/14
 */
@RestController
public class WebController {

    private final AdminService adminService;

    private final UserService userService;

    @Autowired
    public WebController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/")
    public Result<?> hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // 管理员登录
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        }
        // 普通用户登录
        if (RoleEnum.USER.name().equals(account.getRole())) {
            account = userService.login(account);
        }
        return Result.success(account);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // 校验注册用户是否是管理员
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.register(account);
        }
        // 普通用户登录
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.register(account);
        }
        return Result.success();
    }

    /**
     * 修改密码
     *
     * @param account 账户
     * @return {@link Result}<{@link ?}>
     */
    @PutMapping("/updatePassword")
    public Result<?> updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

}
