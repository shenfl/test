package com.test.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author shenfl
 */
@Component
public class PointTransactionListener implements TransactionListener {

    private static final Logger log = LoggerFactory.getLogger(PointTransactionListener.class);

    @Autowired
    private PayService payService;

    /**
     * 根据消息发送的结果 判断是否执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("real send");
        // 根据本地事务执行成与否判断 事务消息是否需要commit与 rollback
        ObjectMapper objectMapper = new ObjectMapper();
        LocalTransactionState state;
        try {
            PointRecord record = objectMapper.readValue(message.getBody(), PointRecord.class);
            payService.pay(record.getOrderNo(), record.getUserId());
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        } catch (UnsupportedEncodingException e) {
            log.error("反序列化消息 不支持的字符编码：{}", e);
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        } catch (IOException e) {
            log.error("反序列化消息失败 io异常：{}", e);
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        System.out.println("模拟故障开始");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("模拟故障结束");
        return state;
    }

    /**
     * RocketMQ 回调 根据本地事务是否执行成功 告诉broker 此消息是否投递成功
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        PointRecord record = null;
        try {
            record = objectMapper.readValue(messageExt.getBody(), PointRecord.class);
        } catch (IOException e) {
            log.error("回调检查本地事务状态异常： ={}", e);

        }
        try {
            //根据是否有transaction_id对应转账记录 来判断事务是否执行成功
            boolean isCommit = payService.checkPayStatus(record.getOrderNo());
            if (isCommit) {
                state = LocalTransactionState.COMMIT_MESSAGE;
            } else {
                state = LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            log.error("回调检查本地事务状态异常： ={}", e);
        }
        return state;

    }
}