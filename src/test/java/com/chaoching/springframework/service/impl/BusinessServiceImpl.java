package com.chaoching.springframework.service.impl;

import com.chaoching.springframework.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
    @Override
    public void doSomething() {
        System.out.println("do some business operations");
    }
}
