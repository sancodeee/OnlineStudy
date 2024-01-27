package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.UserService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody User user) {
        if (StringUtil.isEmpty(user.getUsername())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.add(user);
        return Result.success();
    }

    /**
     * 按id删除
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     *
     * @param ids id
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping("/delete/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        userService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据id更新用户信息
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @PutMapping("/update")
    public Result<?> updateById(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 按id选择
     * 按id查询
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/selectById/{id}")
    public Result<?> selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    /**
     * 查询所有
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/selectAll")
    public Result<?> selectAll(User user) {
        userService.selectAll(user);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param user     用户
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(User user,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {

        return null;
    }

}
