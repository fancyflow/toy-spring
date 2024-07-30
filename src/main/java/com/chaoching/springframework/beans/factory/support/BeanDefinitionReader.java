package com.chaoching.springframework.beans.factory.support;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.core.io.Resource;
import com.chaoching.springframework.core.io.ResourceLoader;

/**
 * 读取bean定义信息，即BeanDefinition的接口
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getBeanDefinitionRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
