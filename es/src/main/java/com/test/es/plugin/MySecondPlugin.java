package com.test.es.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.plugins.Plugin;

public class MySecondPlugin extends Plugin {
    private final static Logger LOGGER = LogManager.getLogger(MyFirstPlugin.class);
    public MySecondPlugin() {
        super();
        LOGGER.warn("This is my second Plugin");
    }
}
