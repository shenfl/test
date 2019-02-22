package com.test.pattern.zerenlian;

/**
 * Created by shenfl on 2018/8/19
 */
public abstract class ChainHandler {

    public void execute(Chain chain, String data) {
        handlerProcess(data);
        chain.proceed(data);
    }

    abstract void handlerProcess(String data);
}
