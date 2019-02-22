package com.test.pattern.zerenlian;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shenfl on 2018/8/19
 * https://www.cnblogs.com/lizo/p/7503862.html
 */
public class ChainClient {
    public static void main(String[] args) {
        List<ChainHandler> list = Arrays.asList(new ChainHandlerA(),
                new ChainHandlerB(), new ChainHandlerC());
        Chain chain = new Chain(list);
        chain.proceed("jack");
        chain.reset();
        chain.proceed("tom");
    }
    static class ChainHandlerA extends ChainHandler {
        @Override
        void handlerProcess(String data) {
            System.out.println("chain A: " + data);
        }
    }
    static class ChainHandlerB extends ChainHandler {
        @Override
        void handlerProcess(String data) {
            System.out.println("chain B: " + data);
        }
    }
    static class ChainHandlerC extends ChainHandler {
        @Override
        void handlerProcess(String data) {
            System.out.println("chain C: " + data);
        }
    }
}
