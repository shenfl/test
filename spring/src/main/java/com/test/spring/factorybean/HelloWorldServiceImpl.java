package com.test.spring.factorybean;

/**
 * Created by shenfl on 2018/8/31
 */
public class HelloWorldServiceImpl implements HelloWorldService {
    public void sayHello() {
        System.out.println("hello world");
    }
    public String getBeanName() {
        return "hello";
    }
}
