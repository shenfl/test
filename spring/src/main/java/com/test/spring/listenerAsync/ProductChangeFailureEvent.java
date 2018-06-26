package com.test.spring.listenerAsync;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class ProductChangeFailureEvent extends ApplicationContextEvent {

    private static final long serialVersionUID = -1681426286796814792L;
    public static final String TASK_KEY = "ProductChangeFailureEvent";

    public ProductChangeFailureEvent(ApplicationContext source) {
        super(source);
    }
}