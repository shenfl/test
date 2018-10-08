package com.test.flink;

import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

/**
 * Created by shenfl on 2018/9/29
 * https://data-artisans.com/blog/a-practical-guide-to-broadcast-state-in-apache-flink
 */
public class TestCollectProcess {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Action> actionStream = env.addSource(new ActionSource());
        KeyedStream<Action, Long> keyedActionStream = actionStream.keyBy((KeySelector<Action, Long>) action -> action.userId);

        MapStateDescriptor<Void, Pattern> bcStateDescriptor =
                new MapStateDescriptor<>(
                        "patterns", Types.VOID, Types.POJO(Pattern.class));
        BroadcastStream<Pattern> patternStream = env.addSource(new PatternSource()).broadcast(bcStateDescriptor);

        BroadcastConnectedStream<Action, Pattern> connect = keyedActionStream.connect(patternStream);
        connect.process(new PatternEvaluator()).setParallelism(2).print(); // parallelism是几后面就每分钟打印几个日志
        env.execute("test collect process");
    }
    static class PatternEvaluator extends KeyedBroadcastProcessFunction<Long, Action, Pattern, Tuple2<Long, Pattern>> {

        ValueState<String> prevActionState;
        @Override
        public void open(Configuration parameters) throws Exception {
            // initialize keyed state
            prevActionState = getRuntimeContext().getState(
                    new ValueStateDescriptor<>("lastAction", Types.STRING));
        }

        @Override
        public void processElement(Action action, ReadOnlyContext ctx, Collector<Tuple2<Long, Pattern>> out) throws Exception {
            // get current pattern from broadcast state
            Pattern pattern = ctx
                    .getBroadcastState(
                            new MapStateDescriptor<>("patterns", Types.VOID, Types.POJO(Pattern.class)))
                    // access MapState with null as VOID default value
                    .get(null);
            // get previous action of current user from keyed state
            String prevAction = prevActionState.value();
            if (pattern != null && prevAction != null) {
                // user had an action before, check if pattern matches
                if (pattern.getFirstAction().equals(prevAction) &&
                        pattern.getSecondAction().equals(action.action)) {
                    // MATCH
                    out.collect(new Tuple2<>(action.userId, pattern));
                }
            }
            // update keyed state and remember action for next pattern evaluation
            prevActionState.update(action.action);
            System.out.println("received data: " + action + " : " + this);
        }

        @Override
        public void processBroadcastElement(Pattern value, Context ctx, Collector<Tuple2<Long, Pattern>> out) throws Exception {
            // store the new pattern by updating the broadcast state
            BroadcastState<Void, Pattern> bcState =
                    ctx.getBroadcastState(new MapStateDescriptor<>("patterns", Types.VOID, Types.POJO(Pattern.class)));
            // storing in MapState with null as VOID default value
            bcState.put(null, value);
            System.out.println("new pattern: " + value.toString() + " : " + this);
        }
    }
    static class ActionSource implements SourceFunction<Action> {

        @Override
        public void run(SourceContext<Action> ctx) throws Exception {
            Action action = new Action(1, "login");
            ctx.collect(action);

            action = new Action(1, "logout");
            ctx.collect(action);
            Thread.sleep(1000);

            action = new Action(2, "buy");
            ctx.collect(action);

            action = new Action(2, "pay");
            ctx.collect(action);

            action = new Action(3, "buy");
            ctx.collect(action);

            action = new Action(3, "pay");
            ctx.collect(action);

            action = new Action(4, "buy");
            ctx.collect(action);

            action = new Action(4, "pay");
            ctx.collect(action);
        }

        @Override
        public void cancel() {

        }
    }
    static class PatternSource implements SourceFunction<Pattern> {

        public PatternSource() {
            System.out.println("new instance pattern source");
        }

        @Override
        public void run(SourceContext<Pattern> ctx) throws Exception {
            while (true) {
                ctx.collect(new Pattern("buy", "pay"));
                Thread.sleep(60000);
            }
        }

        @Override
        public void cancel() {

        }
    }
    static class Action {
        long userId;
        String action;
        public Action(long userId, String action) {
            this.userId = userId;
            this.action = action;
        }
        @Override
        public String toString() {
            return "Action{" +
                    "userId=" + userId +
                    ", action='" + action + '\'' +
                    '}';
        }
    }
}
