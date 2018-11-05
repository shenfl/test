package com.test.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by shenfl on 2018/11/3
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
