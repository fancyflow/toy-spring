package com.chaoching.springframework.beans.factory;

public interface DisposableBean {
    void destroy() throws Exception;
}
