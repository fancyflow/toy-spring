package com.chaoching.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.DisposableBean;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        boolean isDisposableBean = this.bean instanceof DisposableBean;
        if (isDisposableBean) {
            ((DisposableBean) this.bean).destroy();
        }
        if (StrUtil.isNotEmpty(this.destroyMethodName) && (!isDisposableBean || !"destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = null;
            try {
                 destroyMethod = this.bean.getClass().getDeclaredMethod(this.destroyMethodName);
            } catch (NoSuchMethodException e) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            this.invokeCustomDestroyMethod(destroyMethod);
        }
    }

    protected void invokeCustomDestroyMethod(Method method) throws Exception {
        method.setAccessible(true);
        method.invoke(this.bean);
    }
}
