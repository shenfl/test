package com.test.es.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.elasticsearch.hadoop.mr.EsInputFormat;
import org.elasticsearch.hadoop.mr.LinkedMapWritable;

/**
 * https://blog.csdn.net/hellozhxy/article/details/80834695
 */
public class Main {
    public static void main(String[] args) {
        try {
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

            if (args.length != 2) {
                System.err.println("error : " + args.length);
                System.exit(2);
            }
            Job job = Job.getInstance(conf, "souche-search");
            job.setJarByClass(Main.class);
            job.setInputFormatClass(EsInputFormat.class);
            job.setMapperClass(E2HMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            System.out.println(job.waitForCompletion(true));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
