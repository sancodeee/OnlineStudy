package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.UserService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody User user) {
        if (StringUtil.isEmpty(user.getUsername())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.add(user);
        return Result.success();
    }

}
