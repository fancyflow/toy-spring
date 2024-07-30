package com.chaoching.springframework.context.event;

import com.chaoching.springframework.context.ApplicationEvent;
import com.chaoching.springframework.context.ApplicationListener;

public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);
    void removeApplicationListener(ApplicationListener<?> listener);
    void multicastEvent(ApplicationEvent event);
}
