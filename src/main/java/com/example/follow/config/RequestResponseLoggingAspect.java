package com.example.follow.config;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/10
 **/
@Aspect
@Component
@Order(1)
@Log4j2
public class RequestResponseLoggingAspect {

    @Pointcut("execution(public * com.example.follow.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("After " + joinPoint.getSignature().toShortString());
        log.info("Response Data: " + result);
    }
}

