package com.chaoching.springframework.aop;

import com.chaoching.springframework.bean.Animal;
import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.bean.Student;
import com.chaoching.springframework.bean.Yard;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.service.BusinessService;
import org.junit.Test;

public class AutoAopProxyTest {
    @Test
    public void testAutoAopProxy() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:auto-aop-proxy.xml");
        BusinessService businessService = context.getBean("businessService", BusinessService.class);
        System.out.println(businessService);
        businessService.doSomething();

        Car car = context.getBean("car", Car.class);
        System.out.println(car);

        Student student = context.getBean("student", Student.class);
        System.out.println(student);

        Animal animal = context.getBean("animal", Animal.class);
        System.out.println(animal);

        Yard yard = context.getBean("yard", Yard.class);
        System.out.println(yard);

        context.close();
    }
}
