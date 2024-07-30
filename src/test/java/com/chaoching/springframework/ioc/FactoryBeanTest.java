package com.chaoching.springframework.ioc;

import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.bean.Car;
import org.junit.Test;

public class FactoryBeanTest {
    @Test
    public void testFactoryBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:factory-bean.xml");
        Car c1 = applicationContext.getBean("car", Car.class);
        Car c2 = applicationContext.getBean("car", Car.class);
        System.out.println(c1 == c2);
    }
}
