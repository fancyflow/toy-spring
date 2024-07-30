package com.chaoching.springframework.aop;

import com.chaoching.springframework.aop.framework.AdvisorChainFactory;
import com.chaoching.springframework.aop.framework.DefaultAdvisorChainFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvisedSupport {
    // false代表不使用Cglib动态代理，默认使用jdk动态代理
    private boolean proxyTargetClass = false;
    private TargetSource targetSource;
    // 需要增强的方法对应的拦截器链缓存
    private transient Map<Integer, List<Object>> methodCache;
    // 切面对象的列表，存储所有的切面对象，方便生成拦截器链
    private List<Advisor> advisors;
    // 拦截器链获取工厂
    private AdvisorChainFactory advisorChainFactory;

    public AdvisedSupport() {
        this.advisors = new ArrayList<>();
        this.methodCache = new HashMap<>();
        this.advisorChainFactory = new DefaultAdvisorChainFactory();
    }

    // 返回待增强方法对应的拦截器链
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        int cacheKey = method.hashCode();
        List<Object> chain = this.methodCache.get(cacheKey);
        if (chain == null) {
            chain = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass, this);
            this.methodCache.put(cacheKey, chain);
        }
        return chain;
    }

    public boolean isProxyTargetClass() {
        return this.proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return this.targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    public List<Advisor> getAdvisors() {
        return this.advisors;
    }
}
