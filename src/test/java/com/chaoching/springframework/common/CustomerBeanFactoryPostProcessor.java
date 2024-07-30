package com.chaoching.springframework.common;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.PropertyValue;
import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class CustomerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("CustomerBeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition personBeanDefinition = beanFactory.getBeanDefinition("person");
        personBeanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("name", "aaa"));
    }
}
