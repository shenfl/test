package com.test.spring.conversion;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-conversion.xml");

        Student studentService = (Student) ac.getBean("student");
        System.out.println("as: " + studentService.getStudentService().getName() + ":" + studentService.getStudentService().getAge());
    }
}
