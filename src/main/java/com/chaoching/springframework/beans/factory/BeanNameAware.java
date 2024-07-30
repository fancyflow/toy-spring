package com.chaoching.springframework.beans.factory;

/**
 * 实现该接口能够感知到对应的beanName
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String beanName);
}
