package com.chaoching.springframework.beans.factory.support;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.DisposableBean;
import com.chaoching.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.*;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects;
    private final Set<String> singletonsCurrentlyInCreation;
    private final Map<String, DisposableBean> disposableBeans;

    public DefaultSingletonBeanRegistry() {
        this.singletonObjects = new HashMap<>();
        this.singletonsCurrentlyInCreation = new HashSet<>();
        this.disposableBeans = new HashMap<>();
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        this.disposableBeans.put(beanName, disposableBean);
    }

    public Set<String> getSingletonsCurrentlyInCreation() {
        return this.singletonsCurrentlyInCreation;
    }

    public void destroySingletons() {
        List<String> beanNames = new ArrayList<>(this.disposableBeans.keySet());
        for (String beanName : beanNames) {
            DisposableBean disposableBean = this.disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
