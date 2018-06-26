package com.test.spring.listenerAsync;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@WebAppConfiguration
public class SpringSyncAsyncEventsTest {

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void test() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        // execute both urls simultaneously
        mockMvc.perform(get("/products/change-success"));
        mockMvc.perform(get("/products/change-failure"));

        // get stats holder and check if both stats are available:
        // - mail dispatching shouldn't be available because it's executed after a sleep of 5 seconds
        // - product failure should be available because it's executed synchronously, almost immediately (no operations in listeners)
        TaskStatsHolder statsHolder = (TaskStatsHolder) this.wac.getBean("taskStatsHolder");
        TaskStatData mailStatData = statsHolder.getTaskStatHolder(NotifMailDispatchEvent.TASK_KEY);
        TaskStatData productFailureData = statsHolder.getTaskStatHolder(ProductChangeFailureEvent.TASK_KEY);
        assertTrue("Task for mail dispatching is executed after 5 seconds, so at this moment, it taskStatsHolder shouldn't contain it",
                mailStatData == null);
        assertTrue("productFailureHolder shouldn't be null but it is",
                productFailureData != null);
        assertTrue("Product failure listener should be executed within 0 seconds but took "+productFailureData.getExecutionTime()+" seconds",
                productFailureData.getExecutionTime() == 0);
        while (mailStatData == null) {
            mailStatData = statsHolder.getTaskStatHolder(NotifMailDispatchEvent.TASK_KEY);
        }

        // check mail dispatching stats again, when available
        assertTrue("Now task for mail dispatching should be at completed state",
                mailStatData != null);
        assertTrue("Task for mail dispatching should take 5 seconds but it took "+mailStatData.getExecutionTime()+" seconds",
                mailStatData.getExecutionTime() == 5);
        assertTrue("productFailureHolder shouldn't be null but it is",
                productFailureData != null);
        assertTrue("Product failure listener should be executed within 0 seconds but took "+productFailureData.getExecutionTime()+" seconds",
                productFailureData.getExecutionTime() == 0);
        assertTrue("Thread executing mail dispatch and product failure listeners shouldn't be the same",
                !productFailureData.getThreadName().equals(mailStatData.getThreadName()));
        assertTrue("Thread executing product failure listener ("+productFailureData.getThreadName()+") should be the same as current thread ("+Thread.currentThread().getName()+") but it wasn't",
                Thread.currentThread().getName().equals(productFailureData.getThreadName()));
        assertTrue("Thread executing mail dispatch listener ("+mailStatData.getThreadName()+") shouldn't be the same as current thread ("+Thread.currentThread().getName()+") but it was",
                !Thread.currentThread().getName().equals(mailStatData.getThreadName()));
        // make some output to see the informations about tasks
        System.out.println("Data about mail notif dispatching event: "+mailStatData);
        System.out.println("Data about product failure dispatching event: "+productFailureData);
    }
}