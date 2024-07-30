package com.chaoching.springframework.ioc;

import com.chaoching.springframework.bean.*;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.service.BusinessService;
import org.junit.Test;

public class ConverterTest {
    @Test
    public void testConverter() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:converter.xml");
        BusinessService businessService = context.getBean("businessService", BusinessService.class);
        System.out.println(businessService);
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
