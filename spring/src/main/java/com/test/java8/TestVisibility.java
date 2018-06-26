package com.test.java8;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenfl on 2018/6/1
 * https://www.cnblogs.com/sweetchildomine/p/6564627.html
 */
public class TestVisibility {
    static Unsafe unsafe;
    static {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        try {
            unsafe = (Unsafe) f.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("str"+":对应的内存偏移地址"+unsafe.objectFieldOffset(TestVisibility.class.getDeclaredField("str")));
            System.out.println("temporary"+":对应的内存偏移地址"+unsafe.objectFieldOffset(TestVisibility.class.getDeclaredField("temporary")));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    String str = null;//将会被另一个线程修改，当str是volatile时不会出现问题
    String temporary = null;//保存str被修改前的值
    String template = "ABC";//String模板,循环连接直到超出CPU缓存
    String[] arr = new String[2];//当arr是volatile时，不会出现可见性问题

    //构造时连接字符串
    public TestVisibility() {
        StringBuilder sb = new StringBuilder();
        /**
         * 在我的CPU(4790K)上,这里设置为 100000 就能达到终止循环的效果,取决于CPU的缓存大小
         * 把循环次数减少,能达到被CPU缓存,到达死循环的效果
         */
        for (int i = 0; i < 1000; i++) {
            sb.append(template);
        }
        temporary = sb.toString();
        str = sb.toString();
        arr[0] = sb.toString();
        arr[1] = sb.toString();
    }

    public void increment() {
        /**
         * 判断str的值是否等于temporary的值,如果str存在cpu缓存,那么将会是死循环
         * 如果不存在cpu缓存,那么将会去主内存查找,循环将结束
         */
//        System.out.println(unsafe.getObjectVolatile(this, 12));
//        System.out.println(unsafe.getObjectVolatile(this, 16));
//        System.out.println(unsafe.getObjectVolatile(this, 12).equals(unsafe.getObjectVolatile(this, 16)));
//        while (str.equals(temporary)) {
        while (arr[0].equals(arr[1])) {
//        while (unsafe.getObject(this, 12).equals(unsafe.getObject(this, 16))) { // 用unsafe get值，可以避免可见性的问题
//        while (unsafe.getObjectVolatile(this, 12).equals(unsafe.getObjectVolatile(this, 16))) { // 用unsafe get值，可以避免可见性的问题
                // 注意这里不能System.out 因为这样会强制刷新cpu缓存中str的值,原因不明
                // 有的说法是jvm在一个变量读写频率很高的情况下,才不会把数据及时写进内存,但是如果在这里调用System.out.println的话,那么读取str的频率将会变低,所以就写入了内存

                /**
                 * Thread.yield() 可以让步CPU,有可能从主内存中刷新数据
                 * 我的理解是,把CPU让出来让其他线程执行,那么缓存很可能被其他数据给使用了
                 * 导致不得不去主内存中重新读取
                 */
//            Thread.yield();
        }


        // 加锁可以保证可见性
//        while (true) {
//            synchronized (TestVisibility.class) {
//                if (!arr[0].equals(arr[1])) break;
//            }
//        }
    }

    public static void main(String[] args) {
        TestVisibility tv = new TestVisibility();
        //一个线程开启执行循环方法
        new Thread(() -> {
            try {
                tv.increment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            //主线程等待两秒,让循环飞一会
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 另一个线程修改str的值
         */
        System.out.println("abc");
        new Thread(() -> {
//            tv.str = "aa";
//            synchronized (TestVisibility.class) { // 加锁可以保证可见性
            tv.arr[1] = "aa"; // 这儿如果是arr[0]则可以保证可见性，arr[1]就没法保证可见性，所以volatile的数组是没发保证她的元素的可见性的
//            }
//            unsafe.putObject(tv, 12, "aa"); // 没发解决可见性的问题
//            unsafe.putObjectVolatile(tv, 12, "aa"); // 也没发解决可见性的问题
        }).start();
        System.out.println("ddd");
    }
}