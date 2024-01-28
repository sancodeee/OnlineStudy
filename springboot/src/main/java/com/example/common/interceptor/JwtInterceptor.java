package com.example.common.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.flag}")
    private boolean jwtFlag;

    private static AdminService adminService;

    private static UserService userService;

    @Autowired
    public JwtInterceptor(AdminService adminService, UserService userService) {
        JwtInterceptor.adminService = adminService;
        JwtInterceptor.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!jwtFlag) {
            return true;
        }
        // 1. 从http请求的header中获取token
        String token = request.getHeader(Constants.TOKEN);
        if (StrUtil.isBlank(token)) {
            // 如果没拿到，从参数里再拿一次
            token = request.getParameter(Constants.TOKEN);
        }
        // 2. 开始执行认证
        if (StrUtil.isBlank(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        Account account = null;
        try {
            // 解析token获取存储的数据
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];
            String role = userRole.split("-")[1];
            // 根据userId查询数据库
            if (RoleEnum.ADMIN.name().equals(role)) {
                account = adminService.selectById(Integer.valueOf(userId));
            }
            if (RoleEnum.USER.name().equals(role)) {
                account = userService.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        if (ObjectUtil.isNull(account)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            // 验证token
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        return true;
    }
}