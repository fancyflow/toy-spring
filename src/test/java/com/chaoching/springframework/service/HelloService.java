package com.chaoching.springframework.service;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.BeanFactory;
import com.chaoching.springframework.beans.factory.BeanFactoryAware;
import com.chaoching.springframework.context.ApplicationContext;
import com.chaoching.springframework.context.ApplicationContextAware;

public class HelloService implements BeanFactoryAware, ApplicationContextAware {
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}
