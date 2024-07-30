package com.chaoching.springframework.common.event;

import com.chaoching.springframework.context.ApplicationListener;
import com.chaoching.springframework.context.event.ApplicationContextClosedEvent;

public class ApplicationContextClosedEventListener implements ApplicationListener<ApplicationContextClosedEvent> {
    @Override
    public void onApplicationEvent(ApplicationContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }
}
