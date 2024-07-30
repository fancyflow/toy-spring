package com.chaoching.springframework.context.support;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        // 创建bean工厂
        DefaultListableBeanFactory beanFactory = this.createBeanFactory();
        // 解析并注册bean对象的定义类BeanDefinition信息
        this.loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建bean工厂
     * @return
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }
}
