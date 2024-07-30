package com.chaoching.springframework.bean;

import com.chaoching.springframework.beans.factory.annotation.Value;
import com.chaoching.springframework.stereotype.Component;

@Component
public class Animal {
    @Value("${nation}")
    private String nation;
    @Value("${category}")
    private String category;

    @Override
    public String toString() {
        return "Animal{" +
                "nation='" + nation + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
