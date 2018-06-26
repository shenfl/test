package com.test.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by shenfl on 2018/5/25
 */
public class ConsumerPosition {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "0.0.0.0:9092");
        props.put("group.id", "lulu1");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

//        TopicPartition topicPartition = new TopicPartition("shenfl", 1);
//        consumer.assign(Arrays.asList(topicPartition));

        while (true) {
            long position = consumer.position(new TopicPartition("shenfl", 1));
            System.out.println(position);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 没次需要先unsubscribe，否则position就会不变
//            consumer.unsubscribe();
//            consumer.assign(Arrays.asList(topicPartition));
        }
    }
}
