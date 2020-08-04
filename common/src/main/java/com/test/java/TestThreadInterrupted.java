package com.test.java;

public class TestThreadInterrupted extends Thread {
    private TestThreadInterrupted(String name) {
        super(name);
    }
    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println(getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // 回默认把interrupted标示去掉
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TestThreadInterrupted demo1 = new TestThreadInterrupted("first");
        TestThreadInterrupted demo2 = new TestThreadInterrupted("second");
        demo1.start();
        demo2.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        demo1.interrupt();
    }
}
