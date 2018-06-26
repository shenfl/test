package com.test.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * Created by shenfl on 2018/5/25
 */
public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "0.0.0.0:9092");
        props.put("client.id", "he");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        String message = "world ";
        for (int i = 0; i < 10; i++) { // 9
            // 没有key，所以是轮流的方式往partition放
            // ("shenfl", "1", message + i) 如果固定了一个key，则数据都被放到一个分区中了，即使没有指定partitioner
            producer.send(new ProducerRecord<String, String>("lulu", message + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println(recordMetadata.offset());
                    System.out.println(recordMetadata.partition());
                    System.out.println(recordMetadata.toString());
                }
            });
        }
        producer.close();
        System.out.println("over");
    }
}
