package com.test.java8;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by shenfl on 2018/6/28
 * XX:+DisableExplicitGC  错误: 找不到或无法加载主类 XX:+DisableExplicitGC
 */
public class TestExplicitGC {
    private static ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
    public static void main(String[] args) {
        Object obj = new Object();
        WeakReference<Object> wr = new WeakReference(obj,rq);
        System.out.println(wr.get()!=null);
        obj = null;
        System.gc();
        System.out.println(wr.get()!=null);//false，这是因为WeakReference被回收
    }
}
