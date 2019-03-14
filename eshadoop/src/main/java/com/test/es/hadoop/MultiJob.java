package com.test.es.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.elasticsearch.hadoop.mr.EsInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import java.io.IOException;

/**
 * 同时跑两个job
 * https://www.cnblogs.com/zwgblog/p/5993442.html
 */
public class MultiJob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setBoolean("mapred.map.tasks.speculative.execution", false);
        conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
        //ElasticSearch节点
        conf.set("es.nodes", "172.17.40.233");
        conf.set("es.port", "9200");
        conf.set("es.http.timeout","100m");
        conf.set ( "es.output.json" , "true" ); // 设置后会打成json格式，有引号，源博客写法没有引号
        //ElaticSearch Index/Type
        conf.set("es.resource", args[0]); // tangex-clue-test1/sell_chance

        Job job = Job.getInstance(conf, "souche-search");
        job.setJarByClass(MultiJob.class);
        job.setInputFormatClass(EsInputFormat.class);
        job.setMapperClass(E2HMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

//        System.out.println(job.waitForCompletion(true));
        ControlledJob jc = new ControlledJob(job.getConfiguration());



        Configuration conf1 = new Configuration();
        conf1.setBoolean("mapred.map.tasks.speculative.execution", false);
        conf1.setBoolean("mapred.reduce.tasks.speculative.execution", false);
        conf1.set("es.nodes", "172.17.40.233");
        conf1.set("es.resource", "aa/a");
        conf1.set("es.port", "9200");
        conf1.set("es.http.timeout","100m");
        conf1.set("es.input.json", "yes");
        conf1.set("es.mapping.id", "id");
        Job job1 = Job.getInstance(conf1, "write-es");
        job1.setJarByClass(MultiJob.class);
        job1.setMapperClass(H2EMapper.class);
        job1.setMapOutputKeyClass(NullWritable.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputFormatClass(EsOutputFormat.class);
        FileInputFormat.addInputPath(job1, new Path(args[1]));
//        System.out.println(job1.waitForCompletion(true));
        ControlledJob jc1 = new ControlledJob(job1.getConfiguration());
        jc1.addDependingJob(jc);
        JobControl jobControl = new JobControl("RecommendationJob");
        jobControl.addJob(jc);
        jobControl.addJob(jc1);
        Thread jobControlThread = new Thread(jobControl);
        jobControlThread.start();
        while (!jobControl.allFinished()) {
            Thread.sleep(500);
        }
        jobControl.stop();
    }
}
