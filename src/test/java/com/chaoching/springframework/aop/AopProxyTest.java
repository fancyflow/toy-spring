package com.chaoching.springframework.aop;

import com.chaoching.springframework.aop.framework.CglibAopProxy;
import com.chaoching.springframework.aop.framework.JdkDynamicAopProxy;
import com.chaoching.springframework.service.BusinessService;
import org.junit.Before;
import org.junit.Test;

public class AopProxyTest {
    private AdvisedSupport advisedSupport;
    @Before
    public void setup() {
//        BusinessService service = new BusinessServiceImpl();
//
//        this.advisedSupport = new AdvisedSupport();
//        advisedSupport.setTargetSource(new TargetSource(service));
//        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.chaoching.springframework.service.BusinessService.doSomething(..))").getMethodMatcher();
//        advisedSupport.setMethodMatcher(methodMatcher);
//        advisedSupport.setMethodInterceptor(new BusinessServiceInterceptor());
    }

    @Test
    public void testJdkDynamicAopProxy() {
        BusinessService service = (BusinessService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        service.doSomething();
    }

    @Test
    public void testCglibAopProxy() {
        BusinessService service = (BusinessService) new CglibAopProxy(advisedSupport).getProxy();
        service.doSomething();
    }

    @Test
    public void testProxyFactory() {
//        BusinessService jdkDynamicAopProxy = (BusinessService) new ProxyFactory(advisedSupport).getProxy();
//        jdkDynamicAopProxy.doSomething();
//
//        advisedSupport.setProxyTargetClass(true);
//        BusinessService cglibAopProxy = (BusinessService) new ProxyFactory(advisedSupport).getProxy();
//        cglibAopProxy.doSomething();
    }

    @Test
    public void testAdvice() {
//        MethodBeforeAdvice methodBeforeAdvice = new BusinessServiceBeforeAdvice();
//        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(methodBeforeAdvice);
//        advisedSupport.setMethodInterceptor(interceptor);
//
//        BusinessService businessService = (BusinessService) new ProxyFactory(advisedSupport).getProxy();
//        businessService.doSomething();
    }

    @Test
    public void testPointcutAdvisor() {
//        BusinessService businessService = new BusinessServiceImpl();
//        String expression = "execution(* com.chaoching.springframework.service.BusinessService.doSomething(..))";
//        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor(expression);
//        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(new BusinessServiceBeforeAdvice());
//        advisor.setAdvice(interceptor);
//
//        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
//        if (classFilter.matches(businessService.getClass())) {
//            advisedSupport.setTargetSource(new TargetSource(businessService));
//            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
//
//            BusinessService proxyService = (BusinessService) new ProxyFactory(advisedSupport).getProxy();
//            proxyService.doSomething();
//        }
    }
}
