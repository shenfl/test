package com.test.spring.listener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-listener.xml"})
@WebAppConfiguration
public class SpringEventsTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void test() {
        try {
            MvcResult result = mockMvc.perform(get("/testEvent")).andReturn();
            ModelAndView view = result.getModelAndView();
            String expectedView = "success";
            assertTrue("View name from /testEvent should be '"+expectedView+"' but was '"+view.getViewName()+"'", view.getViewName().equals(expectedView));
        } catch (Exception e) {
            e.printStackTrace();
        }
        TimeExecutorHolder timeHolder = (TimeExecutorHolder) this.wac.getBean("timeExecutorHolder");
        int controllerSec = timeHolder.getTestTime("testController").intValue();
        int eventSec = timeHolder.getTestTime("sampleCustomEventListener").intValue();
        assertTrue("Listener for SampleCustomEvent should take 5 seconds before treating the request but it took "+eventSec+" instead",  eventSec == 5);
        assertTrue("Because listener took 5 seconds to response, controller should also take 5 seconds before generating the view, but it took "+controllerSec+ " instead", controllerSec == 5);
    }

    @Test
    public void otherTest() {
        TimeExecutorHolder timeHolder = (TimeExecutorHolder) this.wac.getBean("timeExecutorHolder");
        timeHolder.addNewTime("sampleCustomEventListener", -34);
        try {
            MvcResult result = mockMvc.perform(get("/testOtherEvent")).andReturn();
            ModelAndView view = result.getModelAndView();
            String expectedView = "success";
            assertTrue("View name from /testEvent should be '"+expectedView+"' but was '"+view.getViewName()+"'", view.getViewName().equals(expectedView));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer eventSecObject = timeHolder.getTestTime("sampleCustomEventListener");
        assertTrue("SampleCustomEventListener shouldn't be trigerred on OtherEvent but it was", eventSecObject.intValue() == -34);
    }
}