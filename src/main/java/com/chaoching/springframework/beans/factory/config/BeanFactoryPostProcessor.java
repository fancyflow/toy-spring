package com.chaoching.springframework.beans.factory.config;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * BeanFactory增强器，用户可以自定义BeanFactoryPostProcessor对象
 * 修改BeanFactory对象的属性值，例如修改BeanDefinition值
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有BeanDefintion加载完成后，但在bean实例化之前，提供修改BeanDefinition属性值的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
