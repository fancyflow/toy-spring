package com.chaoching.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;
import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.PropertyValue;
import com.chaoching.springframework.beans.factory.*;
import com.chaoching.springframework.beans.factory.config.*;
import com.chaoching.springframework.core.convert.ConversionService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy;

    public AbstractAutowireCapableBeanFactory() {
        this.instantiationStrategy = new SimpleInstantiationStrategy();
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return this.doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = this.createBeanInstance(beanDefinition);
            // 填充bean属性
            this.populateBean(beanName, bean, beanDefinition);
            // 初始化bean
            bean = this.initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // bean对象创建完成后将其移出正在创建集合
        this.getSingletonsCurrentlyInCreation().remove(beanName);

        // 注册所有销毁bean对象的方法
        this.registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 如果bean为单例才将其放入容器中管理
        if (beanDefinition.isSingleton()) {
            this.addSingleton(beanName, bean);
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return this.instantiationStrategy.instantiate(beanDefinition);
    }

    protected void populateBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 填充bean对象的属性
        this.applyPropertyValues(beanName, bean, beanDefinition);
        // 对使用@Value注解与@Autowired注解的字段进行赋值
        this.resolveDependency(beanName, bean, beanDefinition);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue pv : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = pv.getName();
                Object value = pv.getValue();
                // 通过反射设置属性
                Field field = beanDefinition.getBeanClass().getDeclaredField(name);
                // 如果引用的是另一个bean则去创建或者获取另一个bean
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    String dependentBeanName = beanReference.getBeanName();
                    // 想要注入一个正在创建的bean对象，则说明存在循环依赖
                    if (this.getSingletonsCurrentlyInCreation().contains(dependentBeanName)) {
                        throw new BeansException("The dependencies of some of the beans in the" +
                                " application context form a cycle: [" + beanName + ", " + dependentBeanName + "]");
                    }
                    value = this.getBean(dependentBeanName);
                } else {
                    // 类型转换
                    Class<?> targetType = field.getType();
                    Class<?> sourceType = value.getClass();
                    ConversionService conversionService = this.getConversionService();
                    if (conversionService != null && conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }
                // 暴力反射
                field.setAccessible(true);
                field.set(bean, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    protected void resolveDependency(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : this.getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
            }
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 设置Aware接口相关依赖
        this.invokeAwareMethods(bean, beanName);

        Object wrappedBean = bean;
        // 执行BeanPostProcessor的前置处理方法
        wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行init-method
        try {
            this.invokeInitMethods(wrappedBean, beanName, beanDefinition);
        } catch (Throwable e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 执行BeanPostProcessor的后置处理方法
        wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private void invokeAwareMethods(Object bean, String beanName) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : this.getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 执行bean的初始化方法
     * @param bean
     * @param beanName
     * @param beanDefinition
     * @throws Throwable
     */
    protected void invokeInitMethods(Object bean, String beanName, BeanDefinition beanDefinition) throws Throwable {
        boolean isInitializingBean = bean instanceof InitializingBean;
        if (isInitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        // 避免同时继承自InitializingBean，且自定义方法与InitializingBean方法同名，初始化方法执行两次的情况
        if (StrUtil.isNotEmpty(initMethodName) && (!isInitializingBean || !"afterPropertiesSet".equals(initMethodName))) {
            this.invokeCustomInitMethod(bean, beanName, beanDefinition);
        }
    }

    protected void invokeCustomInitMethod(Object bean, String beanName, BeanDefinition beanDefinition) throws Throwable {
        String initMethodName = beanDefinition.getInitMethodName();
        Method customInitMethod = null;
        try {
            customInitMethod = beanDefinition.getBeanClass().getDeclaredMethod(initMethodName);
        } catch (NoSuchMethodException e) {
            throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
        }
        // 暴力反射
        customInitMethod.setAccessible(true);
        customInitMethod.invoke(bean);
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : this.getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 注册有销毁方法的bean，即bean继承自DisposableBean或则有自定义的销毁方法
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 只有singleton类型bean会执行销毁方法
        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                this.registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return this.instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
