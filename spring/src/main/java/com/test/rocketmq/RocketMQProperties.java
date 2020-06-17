package com.test.rocketmq;

import org.springframework.stereotype.Component;

/**
 * @author shenfl
 */
@Component
public class RocketMQProperties {
    public String getTransactionProducerGroupName() {
        return "shenfl";
    }

    public String getNamesrvAddr() {
        return "localhost:9876";
    }

    public String getTopic() {
        return "testtopic";
    }
}
