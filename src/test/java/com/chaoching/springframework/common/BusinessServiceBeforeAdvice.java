package com.chaoching.springframework.common;

import com.chaoching.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BusinessServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("BeforeAdvice: do something before business operation");
    }
}
