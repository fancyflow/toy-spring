package com.chaoching.springframework.ioc;

import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class CircularReferenceTest {
    @Test
    public void testCircularReference() {
        // 不支持循环依赖，但是可以检测出循环依赖
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:circular-reference.xml");
        Object a = context.getBean("a");
    }
}
