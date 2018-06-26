package com.test.arith;


import java.util.Map;

public class TestString {

    private String input = "jdfoiewjgklmf;lrgk;e";
    private Map<Character, Integer> positions;
    private int max;

    public static void main(String[] args) {
        TestString test = new TestString();
        test.canculate();
        final long[] i = {0};
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    i[0]++;
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)System.out.println(i[0]);
            }
        }).start();
    }

    private void canculate() {
    }

}
