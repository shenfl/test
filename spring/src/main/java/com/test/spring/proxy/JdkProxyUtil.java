package com.test.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by shenfl on 2018/6/17
 */
public class JdkProxyUtil {

    public static  <T> T getProxy( final T t) {
        T result = (T)Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("jdk proxy before...");
                    Object obj = method.invoke(t, args);
                    System.out.println("jdk proxy after...");
                    return obj;
                }
            });
        return result;
    }

}
