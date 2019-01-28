package com.test.spring.importAnnotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * https://blog.csdn.net/pange1991/article/details/81356594
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        Dog bean = applicationContext.getBean(Dog.class);
        System.out.println(bean);
    }
}
