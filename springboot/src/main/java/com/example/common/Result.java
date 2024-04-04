package com.example.common;

import com.example.common.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一响应体
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Getter
@Setter
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1546992917575488946L;

    private String code;
    private String msg;
    private T data;

    private Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        Result<T> tResult = new Result<>();
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setMsg(ResultCodeEnum.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<>(data);
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setMsg(ResultCodeEnum.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> success(String code, String msg, T data) {
        Result<T> tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        tResult.setData(data);
        return tResult;
    }

    public static <T> Result<T> error() {
        Result<T> tResult = new Result<>();
        tResult.setCode(ResultCodeEnum.SYSTEM_ERROR.code);
        tResult.setMsg(ResultCodeEnum.SYSTEM_ERROR.msg);
        return tResult;
    }

    public static <T> Result<T> error(String code, String msg) {
        Result<T> tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        Result<T> tResult = new Result<>();
        tResult.setCode(resultCodeEnum.code);
        tResult.setMsg(resultCodeEnum.msg);
        return tResult;
    }
}
