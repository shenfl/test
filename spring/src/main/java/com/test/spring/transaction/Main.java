package com.test.spring.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by shenfl on 2018/6/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-trans.xml"})
public class Main {

    @Resource
    private AccountService accountService;

    @Test
    public void test() throws IOException {
        // 事务不生效：https://baijiahao.baidu.com/s?id=1661565712893820457&wfr=spider&for=pc
//        accountService.does("ss", "shenfl", 1000);
        // 事务生效
        accountService.pay("ss", "shenfl", 1000);
    }
}
