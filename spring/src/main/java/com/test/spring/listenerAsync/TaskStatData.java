package com.test.spring.listenerAsync;

import java.util.Date;

public class TaskStatData {

    private String threadName;
    private int executionTime;
    private long startTime;
    private long endTime;

    public TaskStatData(String threadName, long startTime, long endTime) {
        this.threadName = threadName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.executionTime = Math.round((endTime - startTime) / 1000);
    }

    public String getThreadName() {
        return threadName;
    }
    public int getExecutionTime() {
        return this.executionTime;
    }
    public long getStartTime() {
        return this.startTime;
    }
    public long getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("TaskStatData {thread name: ").append(this.threadName).append(", start time: ").append(new Date(this.startTime));
        result.append(", end time: ").append(new Date(this.endTime)).append(", execution time: ").append(this.executionTime).append(" seconds}");
        return result.toString();
    }

}