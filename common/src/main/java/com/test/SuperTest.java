package com.test;

public class SuperTest {
    public static void main(String[] args) {
        new Sub().exampleMethod();
    }
}

class Super {
    void interestingMethod() {
        System.out.println("Super's interestingMethod");
    }

    void exampleMethod() {
        interestingMethod();
    }
}

class Sub extends Super {
    void interestingMethod() {
        System.out.println("Sub's interestingMethod");
    }
}