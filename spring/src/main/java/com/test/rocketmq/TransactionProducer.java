package com.test.rocketmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shenfl
 */
@Component
public class TransactionProducer implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TransactionProducer.class);

    private TransactionMQProducer producer;

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Autowired
    private TransactionListener transactionListener;


    /**
     * 构造生产者
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        producer = new TransactionMQProducer(rocketMQProperties.getTransactionProducerGroupName());
        producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("transaction-thread-name-%s").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(30), threadFactory);
        producer.setExecutorService(executor);

        producer.setTransactionListener(transactionListener);

        producer.start();

    }

    /**
     * 真正的事物消息发送者
     */
    public void send() throws JsonProcessingException, UnsupportedEncodingException, MQClientException {

        ObjectMapper objectMapper = new ObjectMapper();

        // 模拟接受前台的支付请求
        String orderNo = UUID.randomUUID().toString();
        Integer userId = 1;
        // 构造发送的事务 消息
        PointRecord record = new PointRecord();
        record.setUserId(userId);
        record.setOrderNo(orderNo);

        Message message = new Message(rocketMQProperties.getTopic(), "", record.getOrderNo(),
                objectMapper.writeValueAsString(record).getBytes(RemotingHelper.DEFAULT_CHARSET));

        System.out.println("start send");
        producer.sendMessageInTransaction(message, null);

        log.info("发送事务消息， topic = {}, body = {}", rocketMQProperties.getTopic(), record);
    }
}