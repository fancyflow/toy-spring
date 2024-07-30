package com.chaoching.springframework.aop;

import com.chaoching.springframework.bean.*;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.service.BusinessService;
import org.junit.Test;

public class MultiAdviceAutoAopProxyTest {
    @Test
    public void testMultiAdviceAutoAopProxy() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:multi-advice-auto-aop-proxy.xml");
        BusinessService businessService = context.getBean("businessService", BusinessService.class);
        businessService.doSomething();

        Car car = context.getBean("car", Car.class);
        System.out.println(car);

        Person person = context.getBean("person", Person.class);
        System.out.println(person);

        Student student = context.getBean("student", Student.class);
        System.out.println(student);

        Animal animal = context.getBean("animal", Animal.class);
        System.out.println(animal);

        Yard yard = context.getBean("yard", Yard.class);
        System.out.println(yard);

        context.close();
    }
}
