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
        props.put("bootstrap.servers", "172.17.40.241:6667");
//        props.put("bootstrap.servers", "0.0.0.0:9092");
        props.put("group.id", "test_sfl_local");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
//        consumer.subscribe(Arrays.asList("jingwei_rt_datasource_test")); //"deviceInfoTopic"
        TopicPartition topicPartition = new TopicPartition("jingwei_rt_datasource_test", 0);
        consumer.assign(Arrays.asList(topicPartition));

        // 方式一
        consumer.seek(new TopicPartition("jingwei_rt_datasource_test", 0), 50866112);

        // 方式二
//        Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
//        map.put(new TopicPartition("shenfl", 0), new OffsetAndMetadata(19424805));
//        consumer.commitSync(map);

        consumer.close();
    }
}
