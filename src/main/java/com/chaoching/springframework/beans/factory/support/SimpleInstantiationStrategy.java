package com.chaoching.springframework.beans.factory.support;

import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.BeansException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /**
     * 简单的bean实例化策略，根据bean的无参构造函数实例化对象
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
        return bean;
    }
}
