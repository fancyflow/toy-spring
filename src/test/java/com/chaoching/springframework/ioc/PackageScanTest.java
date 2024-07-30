package com.chaoching.springframework.ioc;

import com.chaoching.springframework.bean.Animal;
import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class PackageScanTest {
    @Test
    public void testPackageScan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");
        Animal animal = applicationContext.getBean("animal", Animal.class);
        System.out.println(animal);
    }
}
