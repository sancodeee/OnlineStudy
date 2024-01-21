package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.service.impl.AdminServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员前端操作接口
 *
 * @author wangsen
 * @date 2024/01/14
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    @Autowired
    public AdminController(AdminServiceImpl adminServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Admin admin) {
        adminServiceImpl.add(admin);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteById(@PathVariable Integer id) {
        adminServiceImpl.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        adminServiceImpl.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result<?> updateById(@RequestBody Admin admin) {
        if (adminServiceImpl.updateById(admin)) {
            return Result.success();
        } else {
            return Result.error();
        }

    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result<?> selectById(@PathVariable Integer id) {
        Admin admin = adminServiceImpl.selectById(id);
        return Result.success(admin);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result<?> selectAll(Admin admin) {
        List<Admin> list = adminServiceImpl.selectAll(admin);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Admin> page = adminServiceImpl.selectPage(admin, pageNum, pageSize);
        return Result.success(page);
    }

}