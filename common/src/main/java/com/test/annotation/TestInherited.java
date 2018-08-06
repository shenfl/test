package com.test.annotation;

/**
 * Created by shenfl on 2018/8/6
 * 当InheritedAnnotation有Inherited注解时，B也有InheritedAnnotation注解，反之没有
 */
public class TestInherited {
    public static void main(String[] args) {
        Class<B> glass = B.class;
        System.out.println(glass.isAnnotationPresent(InheritedAnnotation.class));
    }
    @InheritedAnnotation
    static class A {

    }
    static class B extends A {

    }
}
