package com.test.netty;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(100000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        System.out.println("aa");
        lock.unlock();
    }
}
