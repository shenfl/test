package com.test.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * https://www.cnblogs.com/1540340840qls/p/7909430.html
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        Object child = applicationContext.getBean("child");
        System.out.println(child);
        Object c = applicationContext.getBean("c");
        System.out.println(c == child);
        Object cc = applicationContext.getBean("cc");
        System.out.println(cc == c);
        Object ccc = applicationContext.getBean("cc");
        System.out.println(ccc == c);
        System.out.println(applicationContext.getBean("user"));
    }
}
