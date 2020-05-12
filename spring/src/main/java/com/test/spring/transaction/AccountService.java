package com.test.spring.transaction;

import java.io.IOException;

/**
 * Created by shenfl on 2018/6/18
 */
public interface AccountService {
    void pay(String out, String in, double money) throws IOException;
    void does(String out, String in, double money) throws IOException;
}
