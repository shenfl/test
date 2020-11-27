package com.test.java;

import java.util.concurrent.*;

public class TestThread {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
        /**
         * 当为14时抛出异常RejectedExecutionException
         */
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            System.out.println("submit " + i);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(finalI + " haha");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(finalI + " over");
                }
            });
        }
        executorService.shutdown();
        System.out.println("over");
    }
}
