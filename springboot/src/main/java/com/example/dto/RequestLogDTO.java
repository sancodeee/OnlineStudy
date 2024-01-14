package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求日志
 *
 * @author wangsen
 * @date 2024/01/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLogDTO {

    /**
     * url
     */
    private String url;
    /**
     * ip
     */
    private String ip;
    /**
     * 类方法
     */
    private String classMethod;
    /**
     * 参数列表
     */
    private Object[] args;
}
