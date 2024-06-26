package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户实体
 *
 * @author wangsen_a
 * @date 2024/02/05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends Account implements Serializable {
    private static final long serialVersionUID = 3860518323079067954L;

    /**
     * ID自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 名字
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 角色
     */
    private String role;
    /**
     * 电话
     */
    private String phone;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 成员
     */
    private String member;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 账户
     */
    private Double account;

}
