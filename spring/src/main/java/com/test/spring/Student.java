package com.test.spring;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by shenfl on 2018/6/17
 */
@Component
public class Student {
    private String name;
    private int score;

    public Student() {
        System.out.println("student create..");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @PostConstruct
    public void init() {
        System.out.println("Student post construct");
    }
}
