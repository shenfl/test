package com.test.spring.transaction;

/**
 * Created by shenfl on 2018/6/18
 */
public interface AccountDao {
    void outMoney(String out, double money);
    void inMoney(String in, double money);
    void outMoney1(String out, double money);
    void inMoney1(String in, double money);
}
