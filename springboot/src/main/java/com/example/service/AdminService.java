package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 管理员服务
 *
 * @author wangsen
 * @date 2024/04/04
 */
public interface AdminService extends IService<Admin> {

    /**
     * 添加
     *
     * @param admin 管理
     */
    void add(Admin admin);

    /**
     * 按id删除
     *
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 删除批处理
     *
     * @param ids id
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link Admin}
     */
    Admin selectById(Integer id);

    /**
     * 查询全部
     *
     * @param admin 管理
     * @return {@link List}<{@link Admin}>
     */
    List<Admin> selectAll(Admin admin);

    /**
     * 分页查询
     *
     * @param admin    管理员
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return {@link PageInfo}<{@link Admin}>
     */
    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    /**
     * 登录
     *
     * @param account 账户
     * @return {@link Account}
     */
    Account login(Account account);

    /**
     * 注册
     *
     * @param account 账户
     */
    void register(Account account);

    /**
     * 更新密码
     *
     * @param account 账户
     */
    void updatePassword(Account account);

}
