package com.test.pattern.zerenlian;


import java.util.Arrays;
import java.util.List;

/**
 * dubbo的filter责任连实现
 * 详见：ProtocolFilterWrapper
 */
public class DubboChain {
    public static void main(String[] args) {
        Invoker invoker = data -> {
            System.out.println("real: " + data);
            return 10;
        };
        Invoker chain = buildInvokerChain(invoker);
        int hello = chain.invoke("hello");
        System.out.println(hello);
    }
    private static Invoker buildInvokerChain(Invoker invoker) {
        Invoker result = invoker;
        List<Filter> filterList = Arrays.asList(new AFilter(), new BFilter());
        for (Filter filter : filterList) {
            Invoker next = result;
            result = data -> filter.invoker(next, data);
        }
        return result;
    }
    interface Filter {
        int invoker(Invoker invoker, String data);
    }
    static class AFilter implements Filter {
        @Override
        public int invoker(Invoker invoker, String data) {
            System.out.println("A before");
            int invoke = invoker.invoke(data);
            System.out.println("A after");
            return invoke;
        }
    }
    static class BFilter implements Filter {
        @Override
        public int invoker(Invoker invoker, String data) {
            System.out.println("B before");
            int invoke = invoker.invoke(data);
            System.out.println("B after");
            return invoke;
        }
    }
}
