package com.test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

public class TestInitSequenceBean implements InitializingBean {

    public TestInitSequenceBean() {
        System.out.println("d");
    }

    /**
     * 在initMethod之前
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ff");
    }

    public void dd() {
        System.out.println("InitSequenceBean: postConstruct");
    }

    public void initMethod() {
        System.out.println("InitSequenceBean: init-method");
    }

    public void say() {
        System.out.println("ll");
    }


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test-allpicationHolder.xml");
        TestInitSequenceBean bean = (TestInitSequenceBean) context.getBean("testInitSequenceBean");
        System.out.println("first: " + bean);
        bean.say();
        A a = new A();
    }
    static class A {
        public A() {
            System.out.println("qq");
        }
        @PostConstruct
        public void ss() {
            System.out.println("ww");
        }
        public void print() {
            System.out.println("pp");
        }
    }
}
