package com.chaoching.springframework.ioc;

import com.chaoching.springframework.bean.Animal;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ValueAnnotationTest {
    @Test
    public void testValueAnnotation() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:value-annotation.xml");
        Animal animal = applicationContext.getBean("animal", Animal.class);
        System.out.println(animal);
    }
}
