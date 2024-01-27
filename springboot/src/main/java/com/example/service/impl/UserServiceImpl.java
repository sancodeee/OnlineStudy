package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Constants;
import com.example.common.enums.MemberEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 添加用户
     *
     * @param user 用户
     */
    @Override
    public void add(User user) {
        // 判空
        if (ObjectUtil.isEmpty(user)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
        // 判断用户是否存在库中
        User dbUser = getBaseMapper().selectByUserName(user.getName());
        if (ObjectUtil.isNotEmpty(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        // 初始化用户没有填写的信息
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setMember(MemberEnum.NO.info);
        user.setRole(RoleEnum.USER.name());
        getBaseMapper().insert(user);
    }

    /**
     * 按id删除
     *
     * @param id id
     */
    @Override
    public void deleteById(Integer id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
        if (getBaseMapper().deleteById(id) == 0) {
            throw new CustomException(ResultCodeEnum.RESOURCE_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
    }

    /**
     * 批量删除
     *
     * @param ids id
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR.code, ResultCodeEnum.PARAM_ERROR.msg);
        }
        if (getBaseMapper().deleteBatchIds(ids) == 0) {
            throw new CustomException(ResultCodeEnum.RESOURCE_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
    }

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link User}
     */
    @Override
    public User selectById(Integer id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
        return getBaseMapper().selectById(id);
    }

    /**
     * 查询所有信息
     *
     * @param user 用户
     * @return {@link List}<{@link User}>
     */
    @Override
    public List<User> selectAll(User user) {
        if (ObjectUtil.isEmpty(user)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Optional.ofNullable(user.getId()).ifPresent(id -> queryWrapper.eq(User::getId, id));
        Optional.ofNullable(user.getName()).ifPresent(name -> queryWrapper.eq(User::getName, name));
        Optional.ofNullable(user.getUsername()).ifPresent(username -> queryWrapper.eq(User::getUsername, username));
        Optional.ofNullable(user.getPassword()).ifPresent(password -> queryWrapper.eq(User::getPassword, password));
        Optional.ofNullable(user.getPhone()).ifPresent(phone -> queryWrapper.eq(User::getPhone, phone));
        Optional.ofNullable(user.getEmail()).ifPresent(email -> queryWrapper.eq(User::getEmail, email));
        Optional.ofNullable(user.getAvatar()).ifPresent(avatar -> queryWrapper.eq(User::getAvatar, avatar));
        Optional.ofNullable(user.getRole()).ifPresent(role -> queryWrapper.eq(User::getRole, role));
        List<User> userList = getBaseMapper().selectList(queryWrapper);
        return CollectionUtils.isEmpty(userList) ? Collections.emptyList() : userList;
    }

    /**
     * 分页查询
     *
     * @param user     用户
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return {@link PageInfo}<{@link User}>
     */
    @Override
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = selectAll(user);
        return PageInfo.of(userList);
    }
}
