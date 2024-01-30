package com.example.common.interceptor;

import cn.hutool.core.text.CharSequenceUtil;
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

    @Value("${jwt.flag:true}")
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
        // jwt拦截器开关
        if (!jwtFlag) {
            return true;
        }
        // 1. 从http请求的header中获取token
        String token = getTokenFromRequest(request);
        // 2. 开始执行认证
        if (CharSequenceUtil.isBlank(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        // 3、解析token并获取账户信息
        Account account = parseTokenAndGetAccount(token);
        // 4、验证token
        validateToken(account, token);
        return true;
    }

    /**
     * 从请求中获取令牌
     *
     * @param request 请求
     * @return {@link String}
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);
        if (CharSequenceUtil.isBlank(token)) {
            token = request.getParameter(Constants.TOKEN);
        }
        return token;
    }

    /**
     * 解析token并获取帐户
     *
     * @param token 令牌
     * @return {@link Account}
     */
    private Account parseTokenAndGetAccount(String token) {
        String userRole;
        try {
            userRole = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        String userId = userRole.split("-")[0];
        String role = userRole.split("-")[1];
        return getAccountByRole(Integer.valueOf(userId), role);
    }

    /**
     * 按角色获取帐户
     *
     * @param userId 用户id
     * @param role   角色
     * @return {@link Account}
     */
    private Account getAccountByRole(Integer userId, String role) {
        if (RoleEnum.ADMIN.name().equals(role)) {
            return adminService.selectById(userId);
        } else if (RoleEnum.USER.name().equals(role)) {
            return userService.selectById(userId);
        }
        // 如果没获取到账户信息就抛异常
        throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
    }

    /**
     * 验证token
     *
     * @param account 账户
     * @param token   令牌
     */
    private void validateToken(Account account, String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
    }

}