package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.Constants;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.impl.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Token工具类
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static AdminServiceImpl staticAdminServiceImpl;

    @Resource
    AdminServiceImpl adminServiceImpl;

    @PostConstruct
    public void setUserService() {
        staticAdminServiceImpl = adminServiceImpl;
    }

    /**
     * 生成token
     *
     * @param tokenData 数据
     * @param password  密码
     * @return {@link String}
     */
    public static String createToken(String tokenData, String password) {
        // 将 userId-role 保存到 token 里面,作为载荷
        return JWT.create().withAudience(tokenData)
                // 2小时后token过期
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                // 以 password 加密后作为token的密钥
                .sign(Algorithm.HMAC256(password));
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return {@link Account}
     */
    public static Account getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.TOKEN);
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (ObjectUtil.isNotEmpty(token)) {
                String userRole = JWT.decode(token).getAudience().get(0);
                // 获取用户id
                String userId = userRole.split("-")[0];
                // 获取角色
                String role = userRole.split("-")[1];
                // 如果是角色是管理员则查库返回账户信息
                if (RoleEnum.ADMIN.name().equals(role)) {
                    // 根据userId查询数据库
                    return staticAdminServiceImpl.selectById(Integer.valueOf(userId));
                }
            }
        } catch (Exception e) {
            log.error("获取当前用户信息出错：", e);
        }
        // 返回空的账号对象
        return new Account();
    }
}

