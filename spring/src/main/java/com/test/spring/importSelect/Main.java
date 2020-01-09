package com.test.spring.importSelect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenfl on 2020-01-09
 * https://www.cnblogs.com/niechen/p/9262452.html
 * 比如当注释掉EnableSpringStudy标签时就会报错
 */
@Configuration
@EnableSpringStudy
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        StudentBean studentBean = (StudentBean) applicationContext.getBean("studentBean");
        System.out.println(studentBean.getName());
    }
}
