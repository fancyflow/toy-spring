package com.chaoching.springframework.aop.framework;

import com.chaoching.springframework.aop.*;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass, AdvisedSupport advisedSupport) {
        List<Object> interceptorList = new ArrayList<>();
        List<Advisor> advisors = advisedSupport.getAdvisors();
        Class<?> actualClass = targetClass != null ? targetClass : method.getDeclaringClass();
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                ClassFilter classFilter = pointcutAdvisor.getPointcut().getClassFilter();
                MethodMatcher methodMatcher = pointcutAdvisor.getPointcut().getMethodMatcher();
                // 检查当前对象是否匹配该切面
                if (classFilter.matches(actualClass) && methodMatcher.matches(method, actualClass)) {
                    interceptorList.add((MethodInterceptor) pointcutAdvisor.getAdvice());
                }
            }
        }
        return interceptorList;
    }
}
