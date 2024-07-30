package com.chaoching.springframework.ioc;

import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.bean.Student;
import com.chaoching.springframework.common.event.CustomEvent;
import org.junit.Test;

public class EventAndEventListenerTest {
    @Test
    public void testEventAndEventListener() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        Student student = applicationContext.getBean("student", Student.class);
        System.out.println(student);
        applicationContext.publishEvent(new CustomEvent(applicationContext));
        applicationContext.close();
    }
}
