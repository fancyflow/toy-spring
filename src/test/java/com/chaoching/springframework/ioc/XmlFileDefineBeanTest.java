package com.chaoching.springframework.ioc;

import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chaoching.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.chaoching.springframework.bean.Person;
import org.junit.Test;

public class XmlFileDefineBeanTest {

    @Test
    public void testXmlFileDefineBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
    }
}
