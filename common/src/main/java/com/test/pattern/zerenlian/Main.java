package com.test.pattern.zerenlian;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenfl
 * dubbo中责任连实现
 */
public class Main {
    public static void main(String[] args) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter.FilterA());
        filters.add(new Filter.FilterB());
        Invoker invoker = data -> {
            System.out.println("main invoker");
            return 5;
        };
        Invoker last = invoker;
        for (Filter filter : filters) {
            final Invoker next = last;
            last = new Invoker() {
                @Override
                public int invoke(String data) {
                    return filter.invoke(next, data);
                }
            };
        }
        System.out.println(last.invoke("hello"));
    }
}
