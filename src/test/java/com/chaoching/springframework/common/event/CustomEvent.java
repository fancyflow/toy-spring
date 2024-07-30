package com.chaoching.springframework.common.event;

import com.chaoching.springframework.context.ApplicationContext;
import com.chaoching.springframework.context.event.ApplicationContextEvent;

public class CustomEvent extends ApplicationContextEvent {
    public CustomEvent(ApplicationContext source) {
        super(source);
    }
}
