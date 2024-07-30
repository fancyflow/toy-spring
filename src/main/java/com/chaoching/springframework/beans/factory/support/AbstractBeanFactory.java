package com.chaoching.springframework.beans.factory.support;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.FactoryBean;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.config.BeanPostProcessor;
import com.chaoching.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.chaoching.springframework.core.convert.ConversionService;
import com.chaoching.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors;
    private final Map<String, Object> factoryBeanObjectCache;
    private final List<StringValueResolver> embeddedValueResolvers;
    private ConversionService conversionService;

    public AbstractBeanFactory() {
        this.beanPostProcessors = new ArrayList<>();
        this.factoryBeanObjectCache = new HashMap<>();
        this.embeddedValueResolvers = new ArrayList<>();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) {
        Object sharedInstance = this.getSingleton(beanName);
        if (sharedInstance != null) {
            // 如果是FactoryBean，则从FactoryBean的getObject方法中创建bean对象
            return this.getObjectForBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);

        // 检测循环依赖，将正在创建的bean对象名称放入一个集合，创建完毕后移出
        this.getSingletonsCurrentlyInCreation().add(beanName);

        Object bean = createBean(beanName, beanDefinition);
        return this.getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 如果是FactoryBean，则从FactoryBean的getObject方法中创建bean对象
     * @param bean
     * @param beanName
     * @return
     */
    protected Object getObjectForBeanInstance(Object bean, String beanName) {
        Object object = bean;
        if (object instanceof FactoryBean) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) object;
            try {
                if (factoryBean.isSingleton()) {
                    // singleton作用域的bean对象直接从缓存中获取
                    object = this.factoryBeanObjectCache.get(beanName);
                    if (object == null) {
                        object = factoryBean.getObject();
                        this.factoryBeanObjectCache.put(beanName, object);
                    }
                } else {
                    // prototype作用域的bean对象每次重新创建，不加入缓存
                    object = factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return object;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return ((T) this.getBean(beanName));
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.containsBeanDefinition(beanName);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 如果存在则覆盖
        if (!this.beanPostProcessors.contains(beanPostProcessor)) {
            this.beanPostProcessors.add(beanPostProcessor);
        }
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolverEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    @Override
    public ConversionService getConversionService() {
        return this.conversionService;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    protected abstract boolean containsBeanDefinition(String beanName);

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
