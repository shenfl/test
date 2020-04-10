package com.test.java;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenfl on 2018/6/2
 * http://www.cnblogs.com/sweetchildomine/p/6559276.html
 */
public class TestWeakHashMap {

    static WeakHashMap<String,String> map = new WeakHashMap<>();
    //会被回收因为 map 的 key 用 new String 实例化了一个对象 保存在堆里,虽然是线程共享,但是并没有任何引用指向这个key
    /**
     * 这里补充一下,Java heap 是被所有线程共享的一块内存区域
     * 几乎所有的对象实例都在这里分配内存,这里说几乎,是因为随着JIT编译器的发展与逃逸分析技术逐渐成熟
     * 栈上分配,标量替换等优化技术将导致一些微妙的变化发生,所有的对象都分配在堆上也渐渐变得不是那么绝对
     */
    static {
        map.put(new String("a"),new String("abc"));
        map.put(new String("b"),new String("abc"));
        map.put(new String("c"),new String("abc"));
        map.put(new String("d"),new String("abc"));
        map.put(new String("e"),new String("abc"));
        map.put(new String("f"),new String("abc"));
        map.put(new String("g"),new String("abc"));
    }

    //会被回收
    static WeakHashMap<String,String> map4 = new WeakHashMap<>();
    static {
        map4.put(new String("a"),"abc");
        map4.put(new String("b"),"abc");
        map4.put(new String("c"),"abc");
        map4.put(new String("d"),"abc");
        map4.put(new String("e"),"abc");
        map4.put(new String("f"),"abc");
        map4.put(new String("g"),"abc");
    }


    static WeakHashMap<String,String> map2 = new WeakHashMap<>();
    //不会被收回,因为存在 方法区(以前也叫永久代,JAVA8已经不存在永久代) - 常量池
    /**
     * (Method Area 别名 Non-Heap) 与Java Heap 一样,是各个线程共享的内存区域,
     * 以前这个区域也叫作 永久代,因为几乎不会被回收
     * 它用于存储已被虚拟机加载的类信息,常量,静态变量.即时编译后的代码等数据
     */
    /**
     * map2的key 是存在 运行时常量池,运行时常量池是 Method Area的一部分
     * Java并不要求常量一定只有在编译期才能产生,运行期间也可能将新的常量放入池中,具有代表性的就是String的intern()方法
     */
    static {
        map2.put("a","abc");
        map2.put("b","abc");
        map2.put("c","abc");
        map2.put("d","abc");
        map2.put("e","abc");
        map2.put("f","abc");
        map2.put("g","abc");
    }

    public static void main(String[] args) throws InterruptedException {
        while(true){
            /**
             * 解开注释,map,map4的key将不会被回收
             * 我理解为,在栈(也叫线程私有栈,或者工作内存)中,每个线程会将共享数据拷贝到栈顶进行运算,
             * 这份数据其实是一个副本.(如果栈内部所包含的"局部变量"是引用,则仅仅是引用值在栈中,而且会占用一个引用本身的大小,具体的对象还是在堆当中,即对象本身的大小与栈空间的使用无关)
             * 所以这个map存在一个引用,就不会去回收它的key
             */
            System.out.println("map:"+map.size());
            System.out.println("map2:"+map2.size());
            System.out.println("map4:"+map4.size());

            //模拟被一个线程调用,然后休眠5秒,会随机被回收
            new Thread(()->{
                System.out.println("map:"+map.size());
                System.out.println("map2:"+map2.size());
                System.out.println("map4:"+map4.size());
                System.out.println("-------------------");
            }).start();
            TimeUnit.SECONDS.sleep(5);
            System.gc();
        }
    }
}