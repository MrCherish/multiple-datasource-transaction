package com.xtt.transaction.aspect;

import org.apache.log4j.MDC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * LogInterceptor
 *
 * @author dexu.tian
 * @date 2020/12/2
 */
@Order(3)
@Aspect
@Component
public class LogInterceptor {

    Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Around(value = "execution(* com.xtt.transaction.controller.*.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj= null;
        try {
            // 请求绑定UUID值，方便搜索日志
            MDC.put("requestId", UUID.randomUUID().toString());
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable.getLocalizedMessage());
            throw throwable;
        } finally {
            MDC.remove("requestId");
        }
        return obj;
    }
}
