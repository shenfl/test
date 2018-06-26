package com.test.es;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T {
    private String a = get();
    private static String b = ggg();
    static {
        System.out.println("df");
    }
    public T() {
        System.out.println("aa");
    }

    public static void main(String[] args) {
        T t = new T();

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
        System.out.println(',');
    }
    private static String get() {
        System.out.println("kk");
        return "sds";
    }
    private static String ggg() {
        System.out.println("ds");
        return "s";
    }
    static class B {
        private String aa = "sdfds";

        @Override
        public String toString() {
            return "B{" +
                    "aa='" + aa + '\'' +
                    '}';
        }
    }
}
