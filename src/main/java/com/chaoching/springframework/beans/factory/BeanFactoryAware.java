package com.chaoching.springframework.beans.factory;

import com.chaoching.springframework.beans.BeansException;

import java.beans.Beans;

/**
 * 实现该接口能感知到所属的BeanFactory
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
