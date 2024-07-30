package com.chaoching.springframework.beans.factory;

import com.chaoching.springframework.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
    /**
     * 返回指定类型的所有bean实例
     * @param type
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanNamesForType(Class<?> requiredType);

    /**
     * 返回定义的所有bean名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
