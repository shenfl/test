package com.test.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

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
        props.put("enable.auto.commit", "true");
        /* 自动确认offset的时间间隔 */
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", "3");
        props.put("auto.offset.reset", "earliest"); // 必须加
        /* key的序列化类 */
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        /* value的序列化类 */
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        /* 定义分区划分策略 */
//        props.put("client.id", "hello");
//        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "com.test.kafka.MyAssignor");
        /* 定义consumer */
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        List<String> list = new ArrayList<>();
        list.add("shenfl");
//        list.add("lulu");
        ConsumerRebalanceListener listener = new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("Revoked: ");
                partitions.forEach(System.out::println);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("Assign: ");
                partitions.forEach(System.out::println);
//                consumer.seekToBeginning(partitions);
//                consumer.seek(new TopicPartition("shenfl", 0), 62);
//                consumer.seek(new TopicPartition("shenfl", 1), 72);
            }
        };
        consumer.subscribe(list, listener);

        int ii = 0;
        int count = 0;
        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(1000);
            poll.count();
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
            if (count == 10) {
                // test max.poll.interval.ms 5分钟后超时及恢复的情况
                System.out.println("start sleep");
                try {
                    Thread.sleep(360000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count++;
//            Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
//            map.put(new TopicPartition("shenfl", 0), new OffsetAndMetadata(36));
//            map.put(new TopicPartition("shenfl", 1), new OffsetAndMetadata(46));
//            consumer.commitSync(map);
            ii++;
//            if (ii == 10) consumer.subscribe(Arrays.asList("shenfl"), listener); // 重新subscribe
            System.out.println(consumer.subscription()); // shenfl
            System.out.println("================================");
        }
    }
}
