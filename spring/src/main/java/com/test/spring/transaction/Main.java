package com.test.spring.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by shenfl on 2018/6/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-trans.xml"})
public class Main {

    @Resource
    private AccountService accountService;

    @Test
    public void test() {
        accountService.pay("shenfl", "lulu", 1000);
    }
}
