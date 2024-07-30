package com.chaoching.springframework.ioc;

import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.bean.Person;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void testApplicationContext() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Car car = applicationContext.getBean("car", Car.class);
        Assertions.assertThat(car.getBrand()).isEqualTo("MZD");
        System.out.println(car);

        Person person = applicationContext.getBean("person", Person.class);
        Assertions.assertThat(person.getName()).isEqualTo("aaa");
        System.out.println(person);
    }
}
