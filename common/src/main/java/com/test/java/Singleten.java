package com.test.java;

/**
 * Created by shenfl on 2018/5/31
 */
public class Singleten {
    private static class Holder {
        private static final Singleten INSTANCE = new Singleten();
    }
    private Singleten() {
        System.out.println("new...");
    }
    public static final Singleten getInstance() {
        return Holder.INSTANCE;
    }
    public static int getNum() {
        return 10;
    }
}
