package com.test.log4j;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class First {
    private static final Logger LOGGER = LoggerFactory.getLogger("first");
    public void print() {
        for (int i = 0; i < 100; i++) {
            LOGGER.error("log in first");
        }
    }
}
