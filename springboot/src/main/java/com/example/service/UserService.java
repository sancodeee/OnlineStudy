package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Account;
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

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link User}
     */
    User selectById(Integer id);

    /**
     * 查询所有
     *
     * @param user 用户
     * @return {@link List}<{@link User}>
     */
    List<User> selectAll(User user);

    /**
     * 分页查询
     *
     * @param user     用户
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @return {@link PageInfo}<{@link User}>
     */
    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    /**
     * 用户登录
     *
     * @param account 账户
     * @return {@link Account}
     */
    Account login(Account account);

    /**
     * 更新密码
     *
     * @param account 账户
     */
    void updatePassword(Account account);


}
