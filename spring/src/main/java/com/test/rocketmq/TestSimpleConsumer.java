package com.test.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.UUID;

/**
 * @author shenfl
 */
public class TestSimpleConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer;
        try {
            consumer = new DefaultMQPushConsumer("my_consumer");
            consumer.setNamesrvAddr("172.17.40.233:9876");
            consumer.setInstanceName(UUID.randomUUID().toString());
            consumer.subscribe("callback", "*");			//指定订阅内容
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);//从上次消费的位置继续消费
            consumer.setMessageModel(MessageModel.CLUSTERING);						//设置为集群消费
            consumer.registerMessageListener(new MessageListenerConcurrently() {	//默认20个线程

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                    for (MessageExt msg : msgs) {
                        System.out.println(new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

                }
            });

            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
