package com.test.java8;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class Concurrent {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);

        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("ss", "SS");
    }
}
