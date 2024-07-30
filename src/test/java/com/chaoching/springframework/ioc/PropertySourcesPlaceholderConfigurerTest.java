package com.chaoching.springframework.ioc;

import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.bean.Person;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class PropertySourcesPlaceholderConfigurerTest {
    @Test
    public void testPropertySourcesPlaceholderConfigurer() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property-sources-placeholder-configurer.xml");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
    }
}
