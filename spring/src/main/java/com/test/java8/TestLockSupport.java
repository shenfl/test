package com.test.java8;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by shenfl on 2018/7/1
 * 测试LockSupport不会释放锁，并且不需要写在锁块里面
 */
public class TestLockSupport {
    private static Object o = new Object();
    static class MyThread extends Thread {
        @Override
        public void run() {
            LockSupport.park();
            synchronized (o) {
                System.out.println("aaaa");
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        MyThread myThread1 = new MyThread();
        myThread1.start();

        myThread.join();
        myThread1.join();
    }
}
