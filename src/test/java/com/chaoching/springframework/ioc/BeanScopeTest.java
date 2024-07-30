package com.chaoching.springframework.ioc;

import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.service.HelloService;
import com.chaoching.springframework.service.TestService;
import org.junit.Test;

public class BeanScopeTest {
    @Test
    public void testBeanScope() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean-scope.xml");
        HelloService h1 = applicationContext.getBean("helloService", HelloService.class);
        HelloService h2 = applicationContext.getBean("helloService", HelloService.class);
        System.out.println(h1 == h2);

        TestService t1 = applicationContext.getBean("testService", TestService.class);
        TestService t2 = applicationContext.getBean("testService", TestService.class);
        System.out.println(t1 == t2);
    }
}
