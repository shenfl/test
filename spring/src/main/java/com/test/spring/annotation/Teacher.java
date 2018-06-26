package com.test.spring.annotation;

import org.springframework.stereotype.Component;

/**
 * Created by shenfl on 2018/6/4
 */
@Component
public class Teacher {

    public Teacher() {
        System.out.println("teacher init");
    }

    public void print() {
        System.out.println("teacher...");
    }
}
