package com.test.pattern.zerenlian;

/**
 * https://time.geekbang.org/column/article/216278
 * 全部handler都要处理
 */
public class HandlerChain1 {

    private Handler1 head = null;
    private Handler1 tail = null;

    public static void main(String[] args) {
        HandlerChain1 chain = new HandlerChain1();
        chain.addHandler(new HandlerA());
        chain.addHandler(new HandlerB());
        chain.handle();
    }

    public void addHandler(Handler1 handler) {
        handler.setSuccessor(null);

        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }

        tail.setSuccessor(handler);
        tail = handler;
    }

    public void handle() {
        if (head != null) {
            head.handle();
        }
    }

    static class HandlerA extends Handler1 {
        @Override
        protected void doHandle() {
            System.out.println("handler A");
        }
    }

    static class HandlerB extends Handler1 {
        @Override
        protected void doHandle() {
            System.out.println("handler B");
        }
    }

    static abstract class Handler1 {
        protected Handler1 successor = null;

        public void setSuccessor(Handler1 successor) {
            this.successor = successor;
        }

        public final void handle() {
            doHandle();
            if (successor != null) {
                successor.handle();
            }
        }

        protected abstract void doHandle();
    }
}


