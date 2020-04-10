package com.test.java;

/**
 * Created by shenfl on 2018/6/4
 * 静态分派
 * https://www.cnblogs.com/java-my-life/archive/2012/06/14/2545381.html
 */
public class Mozi {

    // 如果注释调这个方法则下面的调用会报错
    public void ride(Horse h){
        System.out.println("骑马");
    }

    public void ride(WhiteHorse wh){
        System.out.println("骑白马");
    }

    public void ride(BlackHorse bh){
        System.out.println("骑黑马");
    }

    public static void main(String[] args) {
        Horse wh = new WhiteHorse();
        Horse bh = new BlackHorse(); // BlackHorse bh = new BlackHorse(); //则打印骑黑马
        Mozi mozi = new Mozi();
        mozi.ride(wh);
        mozi.ride(bh);
    }

    static class Horse {}
    static class WhiteHorse extends Horse {}
    static class BlackHorse extends Horse {}

}