package com.chaoching.springframework.bean;

import com.chaoching.springframework.beans.factory.DisposableBean;
import com.chaoching.springframework.beans.factory.InitializingBean;

public class Student implements InitializingBean, DisposableBean {
    private String studentNo;
    private String name;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("method Student#afterPropertiesSet is invoked");
    }

    private void customInitMethod() {
        System.out.println("method Student#customInitMethod is invoked");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("method Student#destroy is invoked");
    }

    public void customDestroyMethod() {
        System.out.println("method Student#customDestroyMethod is invoked");
    }

    public String getStudentNo() {
        return this.studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNo='" + studentNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
