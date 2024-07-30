package com.chaoching.springframework.context.event;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.BeanFactory;
import com.chaoching.springframework.beans.factory.BeanFactoryAware;
import com.chaoching.springframework.context.ApplicationEvent;
import com.chaoching.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    private BeanFactory beanFactory;
    private final Set<ApplicationListener<ApplicationEvent>> applicationListeners;

    public AbstractApplicationEventMulticaster() {
        this.applicationListeners = new HashSet<>();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.remove(listener);
    }

    public Set<ApplicationListener<ApplicationEvent>> ApgetApplicationListeners() {
        return this.applicationListeners;
    }
}
