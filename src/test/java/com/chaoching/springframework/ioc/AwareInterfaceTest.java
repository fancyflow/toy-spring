package com.chaoching.springframework.ioc;

import com.chaoching.springframework.context.support.ClassPathXmlApplicationContext;
import com.chaoching.springframework.service.HelloService;
import org.junit.Test;

public class AwareInterfaceTest {
    @Test
    public void AwareInterface() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aware-interface.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);

        System.out.println(helloService.getBeanFactory());
        System.out.println(applicationContext.getBeanFactory());

        System.out.println(helloService.getApplicationContext());
        System.out.println(applicationContext);
    }
}
