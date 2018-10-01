package com.test.flink;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenfl on 2018/9/28
 * 用connect算子传递配置流
 * https://github.com/streaming-olap/training/blob/master/flink-api-example/src/main/java/Main.java
 */
public class TestCollect {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        if (true) {
            // 设置checkpoint
            env.enableCheckpointing(60000);
            CheckpointConfig checkpointConf = env.getCheckpointConfig();
            checkpointConf.setMinPauseBetweenCheckpoints(30000L);
            checkpointConf.setCheckpointTimeout(10000L);
            checkpointConf.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        } else {
            // non checkpoint
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                    Integer.MAX_VALUE, // number of restart attempts
                    Time.of(5, TimeUnit.SECONDS) // delay
            ));

        }


        //为什么要 broadcast ？
        //配置流
        DataStream<String> configDataStream = env.addSource(new SocketSource())
                .broadcast();

        DataStream<Record>  dataStream = env.addSource(new ConnectSource());


        ConnectedStreams<Record, String> connectedStreams = dataStream.connect(configDataStream);

        DataStream<Record> flatMapDataStream =  connectedStreams.flatMap(new CoFlatMapFunction<Record, String, Record>() {

            private String config;

            @Override
            public void flatMap1(Record record, Collector<Record> collector) throws Exception {
                /*
                 *
                 *
                 * 处理业务逻辑
                 *
                 * */

                if (config.equals("0")) {
                    collector.collect(record);
                } else if(config.equals("1")) {
                    collector.collect(record);
                }
                System.out.println("Received config");
            }

            @Override
            public void flatMap2(String s, Collector<Record> collector) throws Exception {
                /*
                 *   处理配置
                 */

                config = s;
                System.out.println(s);
            }
        });


        SplitStream<Record> splitStream = dataStream.split(new OutputSelector<Record>() {

            @Override
            public Iterable<String> select(Record record) {

                List<String> output = new ArrayList<String>();

                String biz = "" + record.getBizId();
                output.add(biz);

                return output;

            }
        });


        splitStream.select("1").addSink(new SinkFunction<Record>() {
            @Override
            public void invoke(Record record) throws Exception {
                System.out.println(record);
            }
        });

        splitStream.select("2").addSink(new SinkFunction<Record>() {
            @Override
            public void invoke(Record record) throws Exception {
                System.out.println(record);
            }
        });
        env.execute();
    }
    static class ConnectSource implements ParallelSourceFunction<Record> {

        private static Random random = new Random();

        /**
         *
         *  产生数据
         */
        @Override
        public void run(SourceContext<Record> sourceContext) throws Exception {
            while (true) {

                /*
                 *    v1 业务线
                 *    v2 业务Id
                 *    v3 业务属性值
                 *    v4 时间戳
                 *    ....
                 */

                Random random = new Random(100);

                Record record = new Record();


                for (int i = 0 ; i < 4 ; i++) {
                    record.setBizName("" + i);
                    record.setBizId(i);
                    record.setAttr(Integer.valueOf(random.nextInt() / 10));
                    record.setData("json string or other");
                    record.setTimestamp(new Long(System.currentTimeMillis()) / 1000);

                    sourceContext.collect(record);
                }

                Thread.sleep(200);
            }
        }

        /**
         * 关闭资源
         */
        @Override
        public void cancel() {
        }
    }
    static class SocketSource implements SourceFunction<String> {
        private volatile boolean running = true;



        @Override
        public void run(SourceContext<String> sourceContext) throws Exception {
            while (running) {
                try {
                    String response = SocketSource.sendGet("http://172.17.40.234:13080/partition/getMetrics", "");
                    sourceContext.collect(response);
                } catch (Exception e) {
                    //TODO
                }
                Thread.sleep(60000);
            }

        }

        @Override
        public void cancel() {

            running = false;
        }

        public static String sendGet(String url, String param) throws Exception {
            String result = "";
            BufferedReader in = null;

            try {
                String e = url + param;
                URL realUrl = new URL(e);
                URLConnection connection = realUrl.openConnection();
                connection.setConnectTimeout(3000);
                connection.connect();

                String line;
                for(in = new BufferedReader(new InputStreamReader(connection.getInputStream())); (line = in.readLine()) != null; result = result + line) {
                    ;
                }
            } catch (Exception var15) {
                var15.printStackTrace();
                throw new Exception("发送GET请求出现异常！" + var15);
            } finally {
                try {
                    if(in != null) {
                        in.close();
                    }
                } catch (Exception var14) {
                    var14.printStackTrace();
                    throw new Exception("关闭网络请求异常！" + var14);
                }

            }

            return result;
        }
    }
}
