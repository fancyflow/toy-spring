package com.chaoching.springframework.beans.factory.support;

import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.BeansException;

import java.util.*;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap;

    public DefaultListableBeanFactory() {
        this.beanDefinitionMap = new HashMap<>();
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        String[] beanNames = this.getBeanNamesForType(requiredType);
        int length = beanNames == null ? 0 : beanNames.length;
        if (length != 1) {
            throw new BeansException(requiredType + " expected single bean but found " + length + ": " + Arrays.toString(beanNames));
        }
        return this.getBean(beanNames[0], requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                T bean = (T) this.getBean(beanName);
                result.put(beanName, bean);
            }
        }
        return result;
    }

    @Override
    public String[] getBeanNamesForType(Class<?> requiredType) {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            Class<?> beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        return beanNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = this.beanDefinitionMap.keySet();
        return beanNames.toArray(new String[0]);
    }



    @Override
    public void preInstantiateSingletons() throws BeansException {
        Set<String> beanNames = this.beanDefinitionMap.keySet();
        for (String beanName : beanNames) {
            // 只有singleton类型的bean才会被提前实例化
            if (this.beanDefinitionMap.get(beanName).isSingleton()) {
                this.getBean(beanName);
            }
        }
    }
}
