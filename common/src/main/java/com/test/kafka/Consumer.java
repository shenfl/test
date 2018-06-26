package com.test.kafka;

import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;

import java.util.*;

/**
 * Created by shenfl on 2018/5/25
 */
public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        /* 定义kakfa 服务的地址，不需要将所有broker指定上 */
        props.put("bootstrap.servers", "0.0.0.0:9092");
        /* 制定consumer group */
        props.put("group.id", "lulu1");
        /* 是否自动确认offset */
        props.put("enable.auto.commit", "false");
        /* 自动确认offset的时间间隔 */
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
//        props.put("max.poll.records", "3");
        props.put("auto.offset.reset", "earliest"); // 必须加
        /* key的序列化类 */
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        /* value的序列化类 */
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        /* 定义consumer */
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        List<String> list = new ArrayList<>();
        list.add("shenfl");
        consumer.subscribe(list);

        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : poll) {
                System.out.println(record.topic());
                System.out.println(record.partition());
                System.out.println(record.offset());
                System.out.println(record.value());

//                if (record.offset() == 2 && record.partition() == 1) {
//
//                    /**
//                     * 向kafka server提交具体的topic和partition的消费位置
//                     */
//                    Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
//                    map.put(new TopicPartition("shenfl", 0), new OffsetAndMetadata(2));
//                    consumer.commitSync(map);
//
//                    System.out.println("commit");
//                }

                // subscribe方式分配给这个consumer的partition
                System.out.println(consumer.assignment());

                System.out.println("-------------");
            }
            Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
            map.put(new TopicPartition("shenfl", 0), new OffsetAndMetadata(36));
            map.put(new TopicPartition("shenfl", 1), new OffsetAndMetadata(46));
            consumer.commitSync(map);
            System.out.println("================================");
        }
    }
}
