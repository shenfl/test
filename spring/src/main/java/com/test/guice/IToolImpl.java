package com.test.guice;

public class IToolImpl implements ITool {
    @Override
    public void doWork() {
        System.out.println("use tool to work");
    }
}