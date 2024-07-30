package com.chaoching.springframework.common.event;

import com.chaoching.springframework.context.ApplicationListener;
import com.chaoching.springframework.context.event.ApplicationContextRefreshedEvent;

public class ApplicationContextRefreshedEventListener implements ApplicationListener<ApplicationContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextRefreshedEvent event) {
        System.out.println(this.getClass().getName());
    }
}
