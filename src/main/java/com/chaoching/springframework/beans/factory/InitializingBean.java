package com.chaoching.springframework.beans.factory;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
