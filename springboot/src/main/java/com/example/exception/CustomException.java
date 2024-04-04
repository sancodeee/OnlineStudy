package com.example.exception;

import com.example.common.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Setter
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    /**
     * 代码
     */
    private final String code;
    /**
     * 消息
     */
    private final String msg;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

}
