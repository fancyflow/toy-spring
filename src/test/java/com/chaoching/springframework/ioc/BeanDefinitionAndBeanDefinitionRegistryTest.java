package com.chaoching.springframework.ioc;

import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chaoching.springframework.service.TestService;
import org.junit.Test;

public class BeanDefinitionAndBeanDefinitionRegistryTest {

    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(TestService.class);
        beanFactory.registerBeanDefinition("testService", beanDefinition);
        TestService testService = (TestService) beanFactory.getBean("testService");
        System.out.println(testService.test());
        System.out.println(beanFactory.getInstantiationStrategy());
    }
}
