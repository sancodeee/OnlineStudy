package com.example.common.enums;

/**
 * 共同枚举
 *
 * @author wangsen
 * @date 2024/04/04
 */
public enum CommonCons {

    TOKEN("token"),

    USER_DEFAULT_PASSWORD("123456"),

    ;

    public final String msg;

    CommonCons(String msg) {
        this.msg = msg;
    }

}
