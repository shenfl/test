package com.test.spring.transaction;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by shenfl on 2018/6/18
 */
public interface AccountService {
//    @Transactional(rollbackFor = IOException.class) // 注册在接口上也能回滚
    void pay(String out, String in, double money) throws IOException;
    void does(String out, String in, double money) throws IOException;
}
