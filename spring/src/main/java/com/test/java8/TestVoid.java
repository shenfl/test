package com.test.java8;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TestVoid {
    public Void print(String v) {return null;}

    public static void main(String args[]) {
        for(Method method : TestVoid.class.getMethods()) {
            if(method.getReturnType().equals(Void.TYPE)) {
                System.out.println(method.getName());
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            System.out.println(parameterTypes.length);
            for (Class<?> parameterType : parameterTypes) {
                System.out.println(parameterType);
            }
        }
//        Future<Void> =
    }
}
