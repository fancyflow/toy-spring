package com.chaoching.springframework.bean;

public class Person {
    private Car car;
    private Integer age;
    private String name;

    public Person() {
    }

    public Person(Car car, Integer age, String name) {
        this.car = car;
        this.age = age;
        this.name = name;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "car=" + car +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
