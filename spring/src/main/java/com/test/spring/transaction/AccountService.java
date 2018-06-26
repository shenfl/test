package com.test.spring.transaction;

/**
 * Created by shenfl on 2018/6/18
 */
public interface AccountService {
    void pay(String out, String in, double money);
}
