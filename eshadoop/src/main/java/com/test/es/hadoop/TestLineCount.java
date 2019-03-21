package com.test.es.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * https://blog.csdn.net/ecjtusbs/article/details/78708507
 * 从效果看，行号是递增的，即使是两个分片
 */
public class TestLineCount {

    public static void main(String[] args) throws  Exception {

        Configuration configuration=new Configuration();
        Job job=Job.getInstance(configuration, "sum");

        //设定输入类，负责读入分片对应的数据，解析成key-value形式
        job.setInputFormatClass(MyInputFormat.class);

        //指定作业jar包
        job.setJarByClass(TestLineCount.class);

        job.setMapperClass(MyMapper.class);
//        job.setReducerClass(MyReducer.class);

        //reducer个数为2，分别统计奇数行和偶数行之和
//        job.setNumReduceTasks(2);

        //自定义划分分区类
//        job.setPartitionerClass(MyPartitioner.class);

        //输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        if(!job.waitForCompletion(true))
            return;
    }
    static class MyMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

        @Override
        protected void map(LongWritable key, Text value,Context context)
            throws IOException, InterruptedException {

            context.write(key,value);
        }
    }
    static class MyReducer extends Reducer<LongWritable, Text, Text, LongWritable> {

        @Override
        protected void reduce(LongWritable key, Iterable<Text> value,
                              Context content) throws IOException, InterruptedException {

            long sum=0;
            Text tag=null;

            for (Text val:value){
//                sum+=Long.parseLong(val.toString());
                sum++;
            }

            if(key.toString().equals("0")){
                tag=new Text("even: total:");
            }else if(key.toString().equals("1")){
                tag=new Text("odd: total:");
            }
            content.write(tag, new LongWritable(sum));
        }
    }
}