package com.test.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.BroadcastConnectedStream;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * Created by shenfl on 2018/10/8
 */
public class TestNoneKeyedBroadcast {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("group.id", "test_flink");
        properties.put("bootstrap.servers", "0.0.0.0:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("flink.partition-discovery.interval-millis", "10000");

        DataStreamSource<String> dataStream = env.addSource(new FlinkKafkaConsumer010(args[0], new SimpleStringSchema(), properties));

        MapStateDescriptor<Void, String> bcStateDescriptor =
                new MapStateDescriptor<>(
                        "configs", Types.VOID, Types.STRING);
        BroadcastStream<String> configStream = env.addSource(new ConfigSource()).broadcast(bcStateDescriptor);

        BroadcastConnectedStream<String, String> connect = dataStream.connect(configStream);
        connect.process(new MyEvaluator()).print();
        System.out.println(env.getExecutionPlan());
        env.execute("test collect process no keyed");
    }
    static class ConfigSource implements SourceFunction<String> {

        public ConfigSource() {
            System.out.println("new instance config source");
        }

        @Override
        public void run(SourceContext<String> ctx) throws Exception {
            int i = 0;
            while (true) {
                ctx.collect("hello " + i++);
                Thread.sleep(60000);
            }
        }

        @Override
        public void cancel() {

        }
    }
    static class MyEvaluator extends BroadcastProcessFunction<String, String, String> {

        @Override
        public void processElement(String value, ReadOnlyContext ctx, Collector<String> out) throws Exception {
            System.out.println("process value: " + value + " : " + this);
        }

        @Override
        public void processBroadcastElement(String value, Context ctx, Collector<String> out) throws Exception {
            System.out.println("process broadcast value: " + value + " : " + this);
        }
    }
}
