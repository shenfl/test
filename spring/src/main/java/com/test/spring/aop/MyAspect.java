package com.test.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;

/**
 * Created by shenfl on 2018/6/17
 */
public class MyAspect implements Ordered {
    private void log() { // public private都行
        System.out.println("print log");
    }
    private void ret() { // public private都行
        System.out.println("print return");
    }
    private Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around before");
        try {
            Object proceed = proceedingJoinPoint.proceed();
            System.out.println("around after");
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
