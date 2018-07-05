package com.test.spring.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * Created by shenfl on 2018/6/17
 */
@Aspect
public class MyAspectAnno implements Ordered {
    @Before(value = "MyAspectAnno.fn()")
    public void log() {
        System.out.println("annotation log");
    }
    @After(value = "MyAspectAnno.fn()")
    public void after() {
        System.out.println("annotation after");
    }
    // 切面只能织入spring管理的对象，所以||后面的无效
    @Pointcut(value = "execution(public void com.test.spring.proxy.UserDaoImpl.save()) || execution(public void com.test.spring.aop.Main.staticPoint())")
    private void fn() {} // public private无所谓

    @Override
    public int getOrder() { // 值越小优先级高
        return 0;
    }
}