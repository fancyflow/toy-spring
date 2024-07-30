package com.chaoching.springframework.aop.framework;

import com.chaoching.springframework.aop.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

public interface AdvisorChainFactory {
    List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass, AdvisedSupport advisedSupport);
}
