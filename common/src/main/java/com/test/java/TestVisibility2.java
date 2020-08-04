package com.test.java;

import java.io.IOException;

public class TestVisibility2 {
    private static boolean flag = false;
    public static void main(String[] args) {
        com.test.java.TestVisibility2 m = new com.test.java.TestVisibility2();
        for(int i=0;i<20;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    while(!flag){
//                        System.out.println(finalI);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        new Lisenter().start();

    }

    static class Lisenter extends Thread {

        @Override
        public void run() {
            System.out.println("输入&结束迭代");
            while (true) {
                try {
                    int a = System.in.read();
                    if (a == '&') {
                        flag = true;
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Lisenter stop");
        }

    }
}
