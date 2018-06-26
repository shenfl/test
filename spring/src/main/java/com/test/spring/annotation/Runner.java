package com.test.spring.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by shenfl on 2018/6/4
 */
public class Runner {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(FirstConfiguration.class);
        Student student = (Student) applicationContext.getBean("getStudent");
        student.sayHello();
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.print();
    }
}
