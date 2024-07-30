package com.chaoching.springframework.context;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.Aware;

/**
 * 实现该接口能够感知所属的ApplicationContext
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
