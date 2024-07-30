package com.chaoching.springframework.beans.factory;

import com.chaoching.springframework.beans.BeansException;

public interface BeanFactory {
    /**
     * 根据beanName获取对象，beanName不存在时抛出异常
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;

    boolean containsBean(String beanName);
}
