package com.test.flink;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by shenfl on 2018/9/28
 * 在本地模式下传输的数据结构是不需要序列化的，即使有key by
 */
public class TestSerialize {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> source = environment.fromElements(1, 2, 3);
        source.map(new RichMapFunction<Integer, Data>() {
            @Override
            public Data map(Integer integer) throws Exception {
                return new Data(integer);
            }
        }).keyBy(new KeySelector<Data, Integer>() {
            @Override
            public Integer getKey(Data data) throws Exception {
                return data.getNum();
            }
        }).map(new RichMapFunction<Data, String>() {
            @Override
            public String map(Data data) throws Exception {
                return "hello " + data.getNum();
            }
        });
        environment.execute();
    }
    static class Data {
        int num;
        private Data(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
