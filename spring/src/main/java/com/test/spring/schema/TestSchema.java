package com.test.spring.schema;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shenfl on 2018/8/25
 * 自定义schema，阿甘视频
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-schema.xml")
public class TestSchema {
    @Autowired
    private People people;
    @Test
    public void testSchema() {
        System.out.println(people);
    }
}
