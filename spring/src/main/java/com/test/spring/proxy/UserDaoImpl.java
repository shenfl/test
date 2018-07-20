package com.test.spring.proxy;

import com.test.spring.aop.MyAnnotation;

/**
 * Created by shenfl on 2018/6/17
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("user dao save...");
    }

    @Override
    @MyAnnotation(value = "sal")
    public void load() {
        System.out.println("user dao load...");
    }
}
