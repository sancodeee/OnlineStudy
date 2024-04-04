package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.MemberEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.common.enums.CommonCons.USER_DEFAULT_PASSWORD;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${server.port}")
    private String port;

    @Value("${ip}")
    private String ip;

    /**
     * 默认头像
     */
    @Value("${user.default-avatar-name}")
    private String defaultAvatarName;

    /**
     * 添加用户
     *
     * @param user 用户
     */
    @Override
    public void add(User user) {
        // 判空
        Optional.ofNullable(user).orElseThrow(() -> new CustomException(ResultCodeEnum.PARAM_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg));
        // 判断用户是否存在库中
        User dbUser = getBaseMapper().selectByUserName(user.getUsername());
        Optional.ofNullable(dbUser).ifPresent(u -> {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        });
        // 初始化用户没有填写的信息
        if (CharSequenceUtil.isBlank(user.getPassword())) {
            // 新增用户是填充默认密码
            user.setPassword(USER_DEFAULT_PASSWORD.msg);
        }
        if (CharSequenceUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setMember(MemberEnum.NO.info);
        user.setRole(RoleEnum.USER.name());
        // 设置默认头像
        if (CharSequenceUtil.isBlank(user.getAvatar())) {
            // 下载路径
            String http = "http://" + ip + ":" + port + "/files/";
            user.setAvatar(http + defaultAvatarName);
        }
        if (getBaseMapper().insert(user) == 0) {
            throw new CustomException(ResultCodeEnum.FAIL.code, ResultCodeEnum.FAIL.msg);
        }
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
        PageMethod.startPage(pageNum, pageSize);
        List<User> userList = selectAll(user);
        return PageInfo.of(userList);
    }

    /**
     * 普通用户登录
     *
     * @param account 账户
     * @return {@link Account}
     */
    @Override
    public Account login(Account account) {
        Account dbUser = getBaseMapper().selectByUserName(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    /**
     * 更新密码
     *
     * @param account 账户
     */
    @Override
    public void updatePassword(Account account) {
        User dbUser = getBaseMapper().selectByUserName(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        getBaseMapper().updateById(dbUser);
    }

    /**
     * 注册
     *
     * @param account 账户
     */
    @Override
    public void register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);
        add(user);
    }
}
