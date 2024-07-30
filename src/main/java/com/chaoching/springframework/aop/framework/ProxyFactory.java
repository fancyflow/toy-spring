package com.chaoching.springframework.aop.framework;

import com.chaoching.springframework.aop.AdvisedSupport;

public class ProxyFactory extends AdvisedSupport {
    public Object getProxy() {
        return this.createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (!this.isProxyTargetClass() && this.getTargetSource().getTargetClass().length != 0) {
            return new JdkDynamicAopProxy(this);
        }
        return new CglibAopProxy(this);
    }
}
