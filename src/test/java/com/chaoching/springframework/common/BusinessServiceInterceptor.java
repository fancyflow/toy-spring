package com.chaoching.springframework.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class BusinessServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("do something before");
        Object result = methodInvocation.proceed();
        System.out.println("do something after");
        return result;
    }
}
