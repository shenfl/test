package com.test.spring.listenerAsync;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class NotifMailDispatchEvent extends ApplicationContextEvent {

    private static final long serialVersionUID = 9202282810553100778L;
    public static final String TASK_KEY = "NotifMailDispatchEvent";

    public NotifMailDispatchEvent(ApplicationContext source) {
        super(source);
    }
}