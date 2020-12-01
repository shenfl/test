package com.test.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * http://rocketmq.apache.org/docs/simple-example/
 * @author shenfl
 */
public class TestSimpleProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("shenfl");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        long start = System.currentTimeMillis();
        for (int i = 50000; i < 100000; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("testtopic" /* Topic */,
                    "TagA" /* Tag */,
                    ("world " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
//            System.out.printf("%s%n", sendResult);
        }
        System.out.println("cost" + (System.currentTimeMillis()  -start));
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
