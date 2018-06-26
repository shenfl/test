package com.test.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenfl on 2018/6/4
 */
@Configuration
@ComponentScan(basePackages = "com.test.spring.annotation")
public class FirstConfiguration {

    public FirstConfiguration() {
        System.out.println("hello world");
    }

    /**
     * 默认id是方法名
     * @return
     */
    @Bean
    public Student getStudent() {
        return new Student();
    }

}
