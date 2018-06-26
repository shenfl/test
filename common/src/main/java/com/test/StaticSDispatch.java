package com.test;

public class StaticSDispatch {
    static abstract class Human {}
    static class Man extends Human {}
    static class Woman extends Human {}

    public void sayHello(Human guy) {
        System.out.println("hello,guy");
    }

    public void sayHello(Man man) {
        System.out.println("hello,man");
    }

    public void sayHello(Woman woman) {
        System.out.println("hello,woman");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Woman();
        StaticSDispatch sd = new StaticSDispatch();
        sd.sayHello(man);
        sd.sayHello(women);

        Human human = new Man();
        System.out.println(human);
        operate(human);
        System.out.println(human);

    }
    private static void operate(Human human) {
        human = new Man();
    }
}