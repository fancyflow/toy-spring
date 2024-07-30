package com.chaoching.springframework.beans.factory.support;

import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.BeansException;

/**
 * Bean的实例化策略
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
