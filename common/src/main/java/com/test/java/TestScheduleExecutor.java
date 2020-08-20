package com.test.java;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * http://www.mamicode.com/info-detail-2207059.html
 * @author shenfl
 */
public class TestScheduleExecutor {
    public static void main(String[] args) throws InterruptedException {
        //池中只有一个线程
        ScheduledThreadPoolExecutor schedulePool = new ScheduledThreadPoolExecutor(1);
        //作为一个周期任务提交,period 为1000ms,任务执行时间为2000ms
        schedulePool.scheduleWithFixedDelay(new MyRunnable(), 0, 2000, TimeUnit.MILLISECONDS);
        schedulePool.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
        Thread.sleep(11*1000);
        System.out.println("finish: " + System.currentTimeMillis() / 1000);
        schedulePool.shutdown();
    }
    static class MyRunnable implements Runnable {
        int perio = 1;
        @Override
        public void run() {
            //为周期任务捕获异常，避免异常影响下一周期的任务执行
            try {
                System.out.println("---------------第 " + perio + " 周期-------------");
                System.out.println("begin = " + System.currentTimeMillis() / 1000);//秒
                System.out.println("thread: " + Thread.currentThread().getName());
                //任务执行时间
                Thread.sleep(2000);
                System.out.println("end =   " + System.currentTimeMillis() / 1000);
                perio++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
