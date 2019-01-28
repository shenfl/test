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

    /**
     * java代理可以只代理一个接口，不需要实现类，这样像mybatis的mapper用它来执行sql就行了
     * @param args args
     */
    public static void main(String[] args) {
        Class[] clazz = new Class[]{UserDao.class};
        UserDao userDao = (UserDao) Proxy.newProxyInstance(JdkProxyUtil.class.getClassLoader(), clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("he");
                System.out.println("ha");
                return null;
            }
        });
        userDao.save();
    }

}
