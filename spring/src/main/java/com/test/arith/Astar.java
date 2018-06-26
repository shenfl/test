package com.test.arith;

public class Astar {
    public static void main(String[] args){
        Astar pk=new Astar();
        //String类似基本类型，值传递，不会改变实际参数的值
        String test1="Hello";
        pk.change(test1);
        System.out.println(test1);

        //StringBuffer和StringBuilder等是引用传递
        StringBuffer test2=new StringBuffer("Hello");
        pk.change(test2);
        System.out.println(test2.toString());


        int dd = 4;
        pk.change(dd);
        System.out.println(dd);
    }

    private void change(int a) {
        a=a++;
    }

    public void change(String str){
        str=str+"world";
    }

    public void change(StringBuffer str){
        str.append("world");
    }
}
