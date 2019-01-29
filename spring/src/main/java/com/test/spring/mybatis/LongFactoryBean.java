package com.test.spring.mybatis;

import org.springframework.beans.factory.FactoryBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LongFactoryBean implements FactoryBean {

    private Class clazz;

    public LongFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        Class[] arr = new Class[]{clazz};
        Object o = Proxy.newProxyInstance(this.clazz.getClassLoader(), arr, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Annotation annotation = method.getAnnotation(LongSelect.class);
                String sql = ((LongSelect) annotation).sql();
                System.out.println("Run sql: " + sql);
                return null;
            }
        });
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
