package com.test.spring.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class OtherCustomEvent extends ApplicationContextEvent {

    private static final long serialVersionUID = 5236181525834402987L;

    public OtherCustomEvent(ApplicationContext source) {
        super(source);
    }
}