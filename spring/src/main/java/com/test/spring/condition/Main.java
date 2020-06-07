package com.test.spring.condition;

import com.test.spring.importSelect.StudentBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 小马哥spring-boot视频2-7
 * spring-boot的自动化装配就是基于condition的
 * @author shenfl
 */
@Configuration
public class Main {
    @Bean
    @ConditionOnSystemProperties(name = "user.name", value = "zhuky")
    public String helloWorld() {
        return "hello world";
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        String  hello = (String) applicationContext.getBean("helloWorld");
        System.out.println(hello);
    }
}
