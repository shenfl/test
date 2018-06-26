package com.test.java;

/**
 * Created by shenfl on 2018/5/31
 */
public class TestSingle {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Singleten.getNum());
        Thread.sleep(1000);
        System.out.println("after");
        Singleten instance = Singleten.getInstance();
        System.out.println(instance);
    }
}
