package com.test.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * https://time.geekbang.org/column/article/211239?utm_source=baidu-ad&utm_medium=ppzq-pc&utm_campaign=guanwang&utm_content=title&utm_term=baidu-ad-ppzq-title
 * 基于event bus实现观察者模式，还可以实现异步消息处理
 * @author shenfl
 */
public class TestEventBus {

    private EventBus eventBus = new EventBus();

    public void registerObserver(Object observer) {
        eventBus.register(observer);
    }

    public void notify(Object event) {
        eventBus.post(event);
    }

    public static void main(String[] args) {
        TestEventBus eventBus = new TestEventBus();
        eventBus.registerObserver(new AObserver());
        eventBus.notify(22);
    }

    static class AObserver {
        @Subscribe
        public void handleA(String event) {
            System.out.println("handleA");
        }
        @Subscribe
        public void handleA1(Integer event) {
            System.out.println("handleA1");
        }
    }

}
