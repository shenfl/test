package com.test.spring.aop;

import com.test.spring.proxy.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shenfl on 2018/6/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-aop.xml"})
public class Main {

    @Autowired
    private UserDao userDao;

    @Test
    public void test() {
        try {
            userDao.save();
        } catch (Exception e) {
            System.out.println("error");
        }
        System.out.println("--------------");
        Main main = new Main();
        main.staticPoint();
    }
    public void staticPoint() {
        System.out.println("static point");
    }
}
