package com.chaoching.springframework.beans.factory.config;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * InstantiationAwareBeanPostProcessor接口的
     * 此方法在bean对象实例化之前执行
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * InstantiationAwareBeanPostProcessor接口的
     * 此方法在bean对象实例化之后，设置属性时执行
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
