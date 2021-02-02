package com.test.pattern.zerenlian;

import java.util.ArrayList;
import java.util.List;

/**
 * https://time.geekbang.org/column/article/216278
 * 如果有一个handler能处理了，就中断
 */
public class HandlerChain {

    private List<IHandler> handlers = new ArrayList<>();

    public void addHandler(IHandler handler) {
        this.handlers.add(handler);
    }

    public void handle(String data) {
        for (IHandler handler : handlers) {
            boolean handled = handler.handle(data);
            if (handled) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new HandlerA());
        chain.addHandler(new HandlerB());
        chain.handle("hello");
    }

    static class HandlerB implements IHandler {
        @Override
        public boolean handle(String data) {
            boolean handled = false;
            System.out.println("handlerB: " + data);
            return handled;
        }
    }

    static class HandlerA implements IHandler {
        @Override
        public boolean handle(String data) {
            boolean handled = false;
            System.out.println("handlerA: " + data);
            return handled;
        }
    }

    interface IHandler {
        boolean handle(String data);
    }
}
