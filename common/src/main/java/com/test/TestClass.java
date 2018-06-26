package com.test;

import java.lang.invoke.MethodType;

public class TestClass {
    public static void main(String[] args) {
        MethodType methodType = MethodType.methodType(String.class, int.class);
        System.out.println(methodType.toString());
        System.out.println(methodType.toMethodDescriptorString());
        System.out.println(String[].class.isArray());
        System.out.println(String[].class);
        System.out.println(String[].class.getComponentType());


        B b = new B(3, true);
        System.out.println(b.b);
        System.out.println(b.a);

    }
    static class A {
        protected boolean a = false;
        protected void setA(boolean a) {
            this.a = a;
        }
    }
    static class B extends A {
        private int b;
        public B(int b, boolean a) {
            this.b = b;
            setA(a);
        }
    }
}
