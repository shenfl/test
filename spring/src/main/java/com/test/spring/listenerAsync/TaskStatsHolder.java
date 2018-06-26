package com.test.spring.listenerAsync;

import java.util.HashMap;
import java.util.Map;

public class TaskStatsHolder {

    private Map<String, TaskStatData> tasks = new HashMap<String, TaskStatData>();

    public void addNewTaskStatHolder(String key, TaskStatData value) {
        tasks.put(key, value);
    }

    public TaskStatData getTaskStatHolder(String key) {
        return tasks.get(key);
    }
}