package com.test.spring.proxy;

/**
 * Created by shenfl on 2018/6/17
 */
public class Main {
    public static void main(String[] args) {
        UserDao dao = new UserDaoImpl();
        UserDao proxy = JdkProxyUtil.getProxy(dao);
        proxy.save();

        UserDaoImpl proxy1 = CglibProxyUtil.getProxy(UserDaoImpl.class);
        proxy1.save();
        proxy1.load();
    }
}
