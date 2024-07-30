package com.chaoching.springframework.ioc;

import com.chaoching.springframework.beans.PropertyValue;
import com.chaoching.springframework.beans.PropertyValues;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chaoching.springframework.bean.Person;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PopulateBeanWithPropertyValuesTest {

    @Test
    public void testPopulateBeanWithPropertyValues() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "zq"));
        propertyValues.addPropertyValue(new PropertyValue("age", 24));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");
        Assertions.assertThat(person.getName()).isEqualTo("zq");
        Assertions.assertThat(person.getAge()).isEqualTo(24);
        System.out.println(person);
    }
}
