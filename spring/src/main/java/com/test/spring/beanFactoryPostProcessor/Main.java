package com.test.spring.beanFactoryPostProcessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * http://svip.iocoder.cn/Spring/IoC-BeanFactoryPostProcessor/
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-beanFactoryPostProcessor.xml");

        StudentService studentService = (StudentService) ac.getBean("studentService");
        System.out.println("as: " + studentService.getName() + ":" + studentService.getAge());
    }
}
