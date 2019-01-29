package com.test.spring.mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 模拟实现spring_mybatis的实现原理
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        LongDao longDao = (LongDao) context.getBean("longDao");
        longDao.save();
    }
}
