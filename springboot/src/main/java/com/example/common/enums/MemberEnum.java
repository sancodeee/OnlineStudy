package com.example.common.enums;

/**
 * 枚举成员
 *
 * @author wangsen
 * @date 2024/04/04
 */
public enum MemberEnum {
    YES("会员"),
    NO("非会员"),
    ;

    public final String info;

    MemberEnum(String info) {
        this.info = info;
    }
}
