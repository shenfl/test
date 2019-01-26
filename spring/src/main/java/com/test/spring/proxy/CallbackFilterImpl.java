package com.test.spring.proxy;

import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class CallbackFilterImpl implements CallbackFilter {
    @Override
    public int accept(Method method) {

        String methodName = method.getName();
        if ("load".equals(methodName)) {
            return 1; // eat()方法使用callbacks[1]对象拦截。
        } else if ("save".equals(methodName)) {
            return 0; // pay()方法使用callbacks[0]对象拦截。
        }
        return 0;
    }
}

