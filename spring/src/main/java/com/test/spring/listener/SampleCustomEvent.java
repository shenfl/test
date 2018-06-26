package com.test.spring.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class SampleCustomEvent extends ApplicationContextEvent {

    private static final long serialVersionUID = 4236181525834402987L;

    public SampleCustomEvent(ApplicationContext source) {
        super(source);
    }
}