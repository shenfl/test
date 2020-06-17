package com.test.rocketmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.spring.mybatis.LongBeanDefinationRegistry;
import com.test.spring.mybatis.LongDao;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * https://cloud.tencent.com/developer/article/1474391
 * @author shenfl
 */
@Configuration
@ComponentScan(basePackages = "com.test.rocketmq")
@EnableTransactionManagement
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TransactionProducer producer = context.getBean("transactionProducer", TransactionProducer.class);
        try {
            producer.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
