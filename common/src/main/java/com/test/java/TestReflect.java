package com.test.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shenfl on 2018/8/14
 * 测试反射当调用15次以下是NativeMethodAccessorImpl，较慢，当超过15次后使用GeneratedMethodAccessor1，会动态生成字节码运行
 */
public class TestReflect {
    // 如果是private方法，通过正常的反射拿不到
    public static void print(int i) {
        new Exception("" + i).printStackTrace();
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = Class.forName("com.test.java8.TestReflect");
        Method print = aClass.getMethod("print", int.class);
        print.setAccessible(true);
        for (int i = 0; i < 20; i++) {
            print.invoke(null, i);
        }
    }
}
