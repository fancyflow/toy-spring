package com.chaoching.springframework.context.support;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.PropertyValue;
import com.chaoching.springframework.beans.PropertyValues;
import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import com.chaoching.springframework.core.io.DefaultResourceLoader;
import com.chaoching.springframework.core.io.Resource;
import com.chaoching.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

public class PropertySourcesPlaceholderConfigurer extends PlaceholderConfigurerSupport {
    private String location;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性配置文件
        Properties properties = this.loadProperties();

        // 属性值替换占位符
        this.processProperties(beanFactory, properties);

        // 往容器中添加字符串解析器，供解析@Value注解使用
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    /**
     * 加载属性配置文件
     *
     * @return
     */
    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(this.location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    /**
     * 属性值替换占位符
     *
     * @param beanFactory
     * @param properties
     * @throws BeansException
     */
    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            this.resolvePropertyValues(beanDefinition, properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                // TODO 仅简单支持一个占位符的格式
                value = this.resolvePlaceholder((String) value, properties);
                propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
            }
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        StringBuilder buf = new StringBuilder(value);
        int startIndex = value.indexOf(this.placeholderPrefix);
        int endIndex = value.indexOf(this.placeholderSuffix);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = value.substring(startIndex + 2, endIndex);
            String propVal = properties.getProperty(propKey);
            buf.replace(startIndex, endIndex + 1, propVal);
        }
        return buf.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        private final Properties properties;

        private PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertySourcesPlaceholderConfigurer.this.resolvePlaceholder(strVal, this.properties);
        }
    }
}
