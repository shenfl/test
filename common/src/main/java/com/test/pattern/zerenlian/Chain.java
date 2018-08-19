package com.test.pattern.zerenlian;

import java.util.List;

/**
 * Created by shenfl on 2018/8/19
 */
public class Chain {

    private int index;
    private List<ChainHandler> handlers;

    public Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
    }

    public void proceed() {
        if (index >= handlers.size()) return;
        handlers.get(index++).execute(this);
    }

}
