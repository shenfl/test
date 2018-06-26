package com.test.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

public class TestRateLimiter {
    private static RateLimiter one= RateLimiter.create(2);//每秒2个
    private static RateLimiter two=RateLimiter.create(2);//每秒2个
    private TestRateLimiter(){};


    public static void acquire(RateLimiter r,int num){
        double time =r.acquire(num);
        System.out.println("wait time="+time);
    }

    public static void main(String[] args) {
        acquire(one,1);
        acquire(one,1);
        acquire(one,1);
        System.out.println("-----");
        acquire(two,1000000);
        acquire(two,1);
        acquire(two,1);

    }
}
