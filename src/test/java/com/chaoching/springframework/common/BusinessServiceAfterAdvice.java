package com.chaoching.springframework.common;

import com.chaoching.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class BusinessServiceAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterAdvice: do something after business operation");
    }
}
