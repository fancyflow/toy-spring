package com.chaoching.springframework.beans.factory.annotation;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.PropertyValues;
import com.chaoching.springframework.beans.factory.BeanFactory;
import com.chaoching.springframework.beans.factory.BeanFactoryAware;
import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chaoching.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.chaoching.springframework.core.convert.ConversionService;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 处理@Value注解
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 处理@Value注解
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                Object value = valueAnnotation.value();
                value = this.beanFactory.resolverEmbeddedValue((String) value);

                // 类型转换
                Class<?> targetType = field.getType();
                Class<?> sourceType = value.getClass();
                ConversionService conversionService = this.beanFactory.getConversionService();
                if (conversionService != null && conversionService.canConvert(sourceType, targetType)) {
                    value = conversionService.convert(value, targetType);
                }

                // 暴力反射
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    throw new BeansException("Error setting property values for bean: " + beanName, e);
                }
            }
        }
        // 处理@Autowired注解
        for (Field field : fields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (autowiredAnnotation != null) {
                Object dependentBean = null;
                String dependentBeanName = null;
                Class<?> fieldType = field.getType();
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                if (qualifierAnnotation != null) {
                    dependentBeanName = qualifierAnnotation.value();
                } else {
                    String[] beanNames = this.beanFactory.getBeanNamesForType(fieldType);
                    int length = beanNames == null ? 0 : beanNames.length;
                    if (length != 1) {
                        throw new BeansException(fieldType + " expected single bean but found " + length + ": " + Arrays.toString(beanNames));
                    }
                    dependentBeanName = beanNames[0];
                }
                if (this.beanFactory.getSingletonsCurrentlyInCreation().contains(dependentBeanName)) {
                    throw new BeansException("The dependencies of some of the beans in the" +
                            " application context form a cycle: [" + beanName + ", " + dependentBeanName + "]");
                }
                dependentBean = this.beanFactory.getBean(dependentBeanName);
                field.setAccessible(true);
                try {
                    field.set(bean, dependentBean);
                } catch (IllegalAccessException e) {
                    throw new BeansException("Error setting property values for bean: " + beanName, e);
                }
            }
        }
        return pvs;
    }
}
