package com.chaoching.springframework.context.event;

import com.chaoching.springframework.context.ApplicationContext;

public class ApplicationContextRefreshedEvent extends ApplicationContextEvent {
    public ApplicationContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}
