package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService extends IService<Admin> {

    void add(Admin admin);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Admin selectById(Integer id);

    List<Admin> selectAll(Admin admin);

    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    Account login(Account account);

    void register(Account account);

    void updatePassword(Account account);

}
