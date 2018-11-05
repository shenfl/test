package com.test.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shenfl on 2018/11/3
 */
public class OrderEvent {
    /**
     * 计数一共创建了多少对象（跟ring buffer的size一样）
     */
    public static AtomicInteger counter = new AtomicInteger();
    long value;
    public OrderEvent() {
        counter.incrementAndGet();
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
