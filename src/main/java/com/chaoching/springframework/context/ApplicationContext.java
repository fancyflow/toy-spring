package com.chaoching.springframework.context;

import com.chaoching.springframework.beans.factory.HierarchicalBeanFactory;
import com.chaoching.springframework.beans.factory.ListableBeanFactory;
import com.chaoching.springframework.core.io.ResourceLoader;

/**
 * 应用程序上下文
 */
public interface ApplicationContext extends HierarchicalBeanFactory, ListableBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
