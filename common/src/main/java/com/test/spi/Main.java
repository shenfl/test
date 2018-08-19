package com.test.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by shenfl on 2018/8/18
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Developer> developers = ServiceLoader.load(Developer.class);
        Iterator<Developer> iterator = developers.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
