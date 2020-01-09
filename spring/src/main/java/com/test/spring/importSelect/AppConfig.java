package com.test.spring.importSelect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenfl on 2020-01-09
 */
@Configuration
public class AppConfig {

    @Bean
    public StudentBean studentBean() {
        StudentBean studentBean = new StudentBean();
        studentBean.setId(19);
        studentBean.setName("admin");
        return studentBean;
    }
}
