package com.test.spring.jdbc;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by shenfl on 2018/6/17
 */
public class TestJdbcTemplate {
    @Test
    public void test() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://172.17.40.234:3306/canal1");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("INSERT into dept VALUES (NULL, ?, ?)", "shenfl", "ll");
    }
}
