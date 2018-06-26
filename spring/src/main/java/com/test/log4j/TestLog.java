package com.test.log4j;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestLog.class);
        logger.info("Hello World");
        First first = new First();
        first.print();
    }
}
