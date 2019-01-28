package com.test.spring.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by shenfl on 2018/6/17
 */
public class CglibProxyUtil {
    public static <T> T getProxy(Class<T> tClass) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("cglib proxy before");
                Object res = methodProxy.invokeSuper(o, objects);//如果是代理接口，则不能调这个方法
                System.out.println("cglib proxy after");
                return res;
            }
        });
        return (T)enhancer.create();
    }
}
