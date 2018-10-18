package com.test.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * Created by shenfl on 2018/7/31
 */
public class SocketWindowWordCount {

    public static void main(String[] args) throws Exception {

        // the port to connect to
        final int port;
        try {
            final ParameterTool params = ParameterTool.fromArgs(args);
            port = params.getInt("port");
        } catch (Exception e) {
            System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'");
            return;
        }

        // get the execution environment
        Configuration conf = new Configuration();
        conf.setString("mykey","myvalue");
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // get input data by connecting to the socket
        DataStream<String> text = env.socketTextStream("localhost", port, "\n");

        // parse the data, group it, window it, and aggregate the counts
//        DataStream<WordWithCount> windowCounts = text
//                .flatMap(new FlatMapFunction<String, WordWithCount>() {
//                    public void flatMap(String value, Collector<WordWithCount> out) {
//                        for (String word : value.split("\\s")) {
//                            out.collect(new WordWithCount(word, 1L));
//                        }
//                    }
//                })
//                .keyBy("word")
//                .timeWindow(Time.seconds(5), Time.seconds(1))
//                .reduce(new ReduceFunction<WordWithCount>() {
//                    public WordWithCount reduce(WordWithCount a, WordWithCount b) {
//                        return new WordWithCount(a.word, a.count + b.count);
//                    }
//                });

        // print the results with a single thread, rather than in parallel
//        windowCounts.print().setParallelism(1);

        text.flatMap(new LineSplitter()).setParallelism(1)
                // group by the tuple field "0" and sum up tuple field "1"
                .keyBy(0)
                .sum(1).setParallelism(1)
                .print();

        env.execute("Socket Window WordCount");
    }

    // Data type for words with count
    public static class WordWithCount {

        public String word;
        public long count;

        public WordWithCount() {}

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }
    static class LineSplitter extends RichFlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void open(Configuration parameters) throws Exception {
            String string = parameters.getString("mykey", "");
            System.out.println("value: " + string);
        }

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] split = s.toLowerCase().split("\\w+");
            for (String world : split) {
                collector.collect(new Tuple2<>(world, 1));
            }
        }
    }
}
