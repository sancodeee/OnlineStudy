package com.example.common.aop;

import com.example.dto.RequestLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求日志aop
 *
 * @author wangsen
 * @date 2024/01/15
 */
@Slf4j
@Component
@Aspect
public class RequestLogAspect {

    /**
     * 请求开始时间戳
     */
    private long startTime;


    /**
     * 定义切点
     */
    @Pointcut(value = "execution(* com.example.controller.*.*(..))")
    private void log() {
        // 该方法只做为占位符用，并不具有实际执行意义
    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        // 请求开始时间
        startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // url
        String url = request.getRequestURL().toString();
        // 请求ip
        String ip = request.getRemoteAddr();
        // 类名.方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // 参数列表
        Object[] args = joinPoint.getArgs();
        // 封装成requestLog对象
        RequestLogDTO requestLogDTO = new RequestLogDTO(url, ip, classMethod, args);
        // 打印信息
        log.info("==============请求开始=============");
        log.info("请求信息：{}", requestLogDTO);
    }

    @After("log()")
    public void after() {
        long endTime = System.currentTimeMillis();
        // 请求响应时间计算
        long duration = endTime - startTime;
        log.info("==============请求结束，响应时间:{}(ms)=============", duration);
    }

    /**
     * 该方法只在切点方法执行成功后，执行
     * <p>
     * res是切点处执行的方法的返回结果
     *
     * @param res 结果
     */
    @AfterReturning(returning = "res", pointcut = "log()")
    public void afterReturn(Object res) {
        log.info("ResponseResult：{}", res);
    }

}
