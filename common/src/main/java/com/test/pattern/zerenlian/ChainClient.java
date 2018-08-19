package com.test.pattern.zerenlian;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shenfl on 2018/8/19
 */
public class ChainClient {
    public static void main(String[] args) {
        List<ChainHandler> list = Arrays.asList(new ChainHandlerA(),
                new ChainHandlerB(), new ChainHandlerC());
        Chain chain = new Chain(list);
        chain.proceed();
    }
    static class ChainHandlerA extends ChainHandler {
        @Override
        void handlerProcess() {
            System.out.println("chain A");
        }
    }
    static class ChainHandlerB extends ChainHandler {
        @Override
        void handlerProcess() {
            System.out.println("chain B");
        }
    }
    static class ChainHandlerC extends ChainHandler {
        @Override
        void handlerProcess() {
            System.out.println("chain C");
        }
    }
}
