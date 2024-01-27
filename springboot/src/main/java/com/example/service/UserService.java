package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 添加
     *
     * @param user 用户
     */
    void add(User user);

    /**
     * 按id删除
     *
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     *
     * @param ids id
     */
    void deleteBatch(List<Integer> ids);

    User selectById(Integer id);

    /**
     * 查询所有
     *
     * @param user 用户
     * @return {@link List}<{@link User}>
     */
    List<User> selectAll(User user);

    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);


}
