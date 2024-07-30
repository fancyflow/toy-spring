package com.chaoching.springframework.aop;

public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
