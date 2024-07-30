package com.chaoching.springframework.beans.factory;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.config.BeanPostProcessor;
import com.chaoching.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.Set;

public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, ListableBeanFactory, AutowireCapableBeanFactory {
    /**
     * 根据beanName查找对应的BeanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例bean对象
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    Set<String> getSingletonsCurrentlyInCreation();
}
