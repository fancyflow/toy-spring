package com.chaoching.springframework.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.BeanFactory;

/**
 * BeanFactory的继承层次和Spring框架保持一致
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 执行BeanPostProcessor的postProcessBeforeInitialization方法
     * @param existingBean
     * @param beanName
     * @throws BeanException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException;

    /**
     * 执行BeanPostProcessor的postProcessAfterInitialization方法
     * @param existingBean
     * @param beanName
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
