package com.chaoching.springframework.context.event;

import com.chaoching.springframework.context.ApplicationContext;

public class ApplicationContextClosedEvent extends ApplicationContextEvent {
    public ApplicationContextClosedEvent(ApplicationContext source) {
        super(source);
    }
}
