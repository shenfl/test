package com.test.spring.proxy;

/**
 * Created by shenfl on 2018/6/17
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("user dao save...");
    }
}
