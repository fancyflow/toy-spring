package com.chaoching.springframework.ioc;

import com.chaoching.springframework.bean.Animal;
import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.bean.Student;
import com.chaoching.springframework.bean.Yard;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class AutowiredAnnotationTest {
    @Test
    public void testAutowiredAnnotation() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");

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
