package com.test.spring.listener;

import java.util.HashMap;
import java.util.Map;

public class TimeExecutorHolder {

    private Map<String, Integer> testTimes = new HashMap();

    public void addNewTime(String key, Integer value) {
        testTimes.put(key, value);
    }

    public Integer getTestTime(String key) {
        return testTimes.get(key);
    }
}