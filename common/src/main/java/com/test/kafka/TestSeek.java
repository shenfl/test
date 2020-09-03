package com.test.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by shenfl on 2018/4/20
 */
public class TestSeek {
    public static void main(String[] args) {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "172.17.40.241:6667");
        props.put("bootstrap.servers", "10.255.15.154:9082,10.255.15.153:9082,10.255.15.155:9082");
        props.put("group.id", "test_sfl-test_canal_tunnel");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);

        work(consumer, 0l, 1l, 2l);

        consumer.close();
    }

    private static void work(KafkaConsumer<String, String> consumer, long partition0, long partition1, long partition2) {
//        consumer.subscribe(Arrays.asList("jingwei_rt_datasource_test")); //"deviceInfoTopic"
        TopicPartition topicPartition = new TopicPartition("test_canal_tunnel", 0);
        consumer.assign(Arrays.asList(topicPartition));

        // 方式一
        consumer.seek(new TopicPartition("test_canal_tunnel", 0), partition0);

        // 方式二
//        Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
//        map.put(new TopicPartition("test_canal_tunnel", 2), new OffsetAndMetadata(359463));
//        consumer.commitSync(map);

        topicPartition = new TopicPartition("test_canal_tunnel", 1);
        consumer.assign(Arrays.asList(topicPartition));
        consumer.seek(new TopicPartition("test_canal_tunnel", 1), partition1);
        topicPartition = new TopicPartition("test_canal_tunnel", 2);
        consumer.assign(Arrays.asList(topicPartition));
        consumer.seek(new TopicPartition("test_canal_tunnel", 2), partition2);
    }
}
