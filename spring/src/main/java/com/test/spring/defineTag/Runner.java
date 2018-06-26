package com.test.spring.defineTag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-mytag.xml");

        DataSourceWraper user = (DataSourceWraper) ac.getBean("dataSourceWraper");
        System.out.println("as: " + user.getDataSource());
    }
}
