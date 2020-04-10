package com.test.java;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by shenfl on 2018/6/2
 * https://blog.csdn.net/dfdsggdgg/article/details/51543545
 */
public class UnsafePlayer {

    public static void main(String[] args) throws Exception {
        //通过反射实例化Unsafe
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        //实例化Player
        Player player = (Player) unsafe.allocateInstance(Player.class);
        player.setAge(18);
        player.setName("li lei");
        for(Field field:Player.class.getDeclaredFields()){
            System.out.println(field.getName()+":对应的内存偏移地址"+unsafe.objectFieldOffset(field));
        }
        System.out.println("-------------------");

        int ageOffset= 12;
        //修改内存偏移地址为8的值（age）,返回true,说明通过内存偏移地址修改age的值成功
        System.out.println(unsafe.compareAndSwapInt(player, ageOffset, 18, 20));
        System.out.println("age修改后的值："+player.getAge());
        System.out.println("-------------------");

        //修改内存偏移地址为8的值，但是修改后不保证立马能被其他的线程看到。
        unsafe.putOrderedInt(player, 12, 33);
        System.out.println("age修改后的值："+player.getAge());
        System.out.println("-------------------");

        //修改内存偏移地址为12的值，volatile修饰，修改能立马对其他线程可见
        unsafe.putObjectVolatile(player, 16, "han mei");
        System.out.println("name修改后的值："+unsafe.getObjectVolatile(player, 16));
    }
}

class Player{

    private int age;

    private String name;

    private Player(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}