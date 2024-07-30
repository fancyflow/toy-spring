package com.chaoching.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法前置通知
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
