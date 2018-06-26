package com.test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by shenfl on 2018/4/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test-allpicationHolder.xml"})
@WebAppConfiguration
public class TestApplicationHolder {
    @Test
    public void test() {
        ApplicationContext context = ApplicationContextHolder.getContext();
        System.out.println("------------");
        System.out.println(context);
        Object locator = context.getBean("user");
        System.out.println(locator);
    }
}
