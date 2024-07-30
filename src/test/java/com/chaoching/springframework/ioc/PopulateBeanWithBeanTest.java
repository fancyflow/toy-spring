package com.chaoching.springframework.ioc;

import com.chaoching.springframework.beans.PropertyValue;
import com.chaoching.springframework.beans.PropertyValues;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.config.BeanReference;
import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.bean.Person;
import org.junit.Test;

import java.math.BigDecimal;

public class PopulateBeanWithBeanTest {
    @Test
    public void testPopulateBeanWithBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        PropertyValues carPropertyValues = new PropertyValues();
        carPropertyValues.addPropertyValue(new PropertyValue("brand", "BMW"));
        carPropertyValues.addPropertyValue(new PropertyValue("price", new BigDecimal("1800000")));
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, carPropertyValues);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);

        PropertyValues personPropertyValues = new PropertyValues();
        personPropertyValues.addPropertyValue(new PropertyValue("car", new BeanReference("car")));
        personPropertyValues.addPropertyValue(new PropertyValue("name", "zq"));
        personPropertyValues.addPropertyValue(new PropertyValue("age", 24));
        BeanDefinition personBeanDefinition = new BeanDefinition(Person.class, personPropertyValues);
        beanFactory.registerBeanDefinition("person", personBeanDefinition);
        Person person = (Person) beanFactory.getBean("person");

        System.out.println(person);
    }
}
