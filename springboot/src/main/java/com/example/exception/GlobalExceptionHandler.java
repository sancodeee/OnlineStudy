package com.example.exception;

import com.example.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理程序
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.controller")
public class GlobalExceptionHandler {

    /**
     * 统一异常处理@ExceptionHandler,主要用于Exception
     *
     * @param request 请求
     * @param e       e
     * @return {@link Result}
     */
    @ExceptionHandler(Exception.class)
    public Result<?> error(HttpServletRequest request, Exception e) {
        log.error("异常信息：", e);
        return Result.error("500", "服务器发生不明异常");
    }

    @ExceptionHandler(CustomException.class)
    public Result<?> customError(HttpServletRequest request, CustomException e) {
        return Result.error(e.getCode(), e.getMsg());
    }
}
