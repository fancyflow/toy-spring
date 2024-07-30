package com.chaoching.springframework.aop;

import com.chaoching.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.chaoching.springframework.service.TestService;
import org.junit.Test;

import java.lang.reflect.Method;

public class PointcutExpressionTest {
    @Test
    public void testPointcutExpression() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.chaoching.springframework.service.TestService.*(..))");
        Class<TestService> clazz = TestService.class;

        System.out.println(pointcut.matches(clazz));

        Method method = clazz.getDeclaredMethod("test");
        System.out.println(pointcut.matches(method, clazz));
    }
}
