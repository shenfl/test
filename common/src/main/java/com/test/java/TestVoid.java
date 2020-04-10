package com.test.java;

import java.lang.reflect.Method;

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
