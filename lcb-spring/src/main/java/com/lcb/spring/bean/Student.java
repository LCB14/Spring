package com.lcb.spring.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author changbao.li
 * @Description 学生bean
 * @Date 2019-06-22 16:00
 */
@Component
public class Student {

    private String name;

    private School school;

    private int age;

    public Student(School school) {
        this.school = school;
    }

    @PostConstruct
    public void init(){
        System.out.println("init");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", school=" + school +
                ", age=" + age +
                '}';
    }
}
