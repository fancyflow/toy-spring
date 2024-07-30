package com.chaoching.springframework.bean;

import com.chaoching.springframework.beans.factory.annotation.Autowired;
import com.chaoching.springframework.beans.factory.annotation.Value;
import com.chaoching.springframework.stereotype.Component;

@Component
public class Yard {
    @Value("${name}")
    private String owner;
    @Autowired
    private Car car;
    @Autowired
    private Animal animal;

    @Override
    public String toString() {
        return "Yard{" +
                "owner='" + owner + '\'' +
                ", car=" + car +
                ", animal=" + animal +
                '}';
    }
}
