package com.chaoching.springframework.context.event;

import com.chaoching.springframework.context.ApplicationContext;
import com.chaoching.springframework.context.ApplicationEvent;

public abstract class ApplicationContextEvent extends ApplicationEvent {
    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) this.getSource();
    }
}
