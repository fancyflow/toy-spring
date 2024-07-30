package com.chaoching.springframework.beans.factory.config;

import com.chaoching.springframework.beans.factory.HierarchicalBeanFactory;
import com.chaoching.springframework.core.convert.ConversionService;
import com.chaoching.springframework.util.StringValueResolver;

/**
 * BeanFactory的继承层次和Spring框架保持一致
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    /**
     * 用于注册BeanPostProcessor对象，方便后续对bean对象进行扩展
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁所有bean对象
     */
    void destroySingletons();

    /**
     * 添加嵌入参数解析器
     * @param valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 解析嵌入的参数
     * @param value
     * @return
     */
    String resolverEmbeddedValue(String value);

    ConversionService getConversionService();

    void setConversionService(ConversionService conversionService);
}
