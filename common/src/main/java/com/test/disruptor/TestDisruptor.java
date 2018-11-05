package com.test.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shenfl on 2018/11/3
 */
public class TestDisruptor {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                new OrderEventFactory(),
                1024,
                service,
                ProducerType.SINGLE,
                new BlockingWaitStrategy()
        );
        disruptor.handleEventsWith(new OrderEventHandler());
        disruptor.start();
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        for (int i = 0; i < 100000; i++) {
            long next = ringBuffer.next();
            try {
                OrderEvent orderEvent = ringBuffer.get(next);
                orderEvent.setValue(i);
            } finally {
                ringBuffer.publish(next);
            }
        }
        disruptor.shutdown();
        service.shutdown();
        System.out.println(OrderEvent.counter.get());
    }
}
