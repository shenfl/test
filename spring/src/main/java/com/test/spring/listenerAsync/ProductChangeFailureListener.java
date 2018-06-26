package com.test.spring.listenerAsync;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProductChangeFailureListener
        implements ApplicationListener<ProductChangeFailureEvent> {

    @Override
    public void onApplicationEvent(ProductChangeFailureEvent event) {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        ((TaskStatsHolder) event.getApplicationContext().getBean("taskStatsHolder")).addNewTaskStatHolder(ProductChangeFailureEvent.TASK_KEY, new TaskStatData(Thread.currentThread().getName(), start, end));
    }

}