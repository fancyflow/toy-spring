package com.chaoching.springframework.ioc;

import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chaoching.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.bean.Person;
import com.chaoching.springframework.common.CustomerBeanFactoryPostProcessor;
import com.chaoching.springframework.common.CustomerBeanPostProcessor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class BeanFactoryPostProcessorAndBeanPostProcessorTest {
    @Test
    public void testBeanFactoryPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 在所有BeanDefinition加载完成后，但在bean实例化之前，修改BeanDefinition的属性值
        CustomerBeanFactoryPostProcessor customerBeanFactoryPostProcessor = new CustomerBeanFactoryPostProcessor();
        customerBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        Person person = beanFactory.getBean("person", Person.class);
        Assertions.assertThat(person.getName()).isEqualTo("aaa");
        System.out.println(person);
    }

    @Test
    public void testBeanPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 添加bean实例化后的处理器
        CustomerBeanPostProcessor customerBeanPostProcessor = new CustomerBeanPostProcessor();
        beanFactory.addBeanPostProcessor(customerBeanPostProcessor);

        Car car = beanFactory.getBean("car", Car.class);
        Assertions.assertThat(car.getBrand()).isEqualTo("MZD");
        System.out.println(car);
    }
}
