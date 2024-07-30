package com.chaoching.springframework.common;

import com.chaoching.springframework.bean.Car;
import com.chaoching.springframework.beans.factory.FactoryBean;

import java.math.BigDecimal;

public class CarFactoryBean implements FactoryBean<Car> {
    private String brand;
    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(this.brand);
        car.setPrice(new BigDecimal("1800000"));
        return car;
    }

    @Override
    public Class<Car> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
