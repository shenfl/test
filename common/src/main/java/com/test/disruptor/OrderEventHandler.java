package com.test.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by shenfl on 2018/11/3
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getValue());
    }
}
