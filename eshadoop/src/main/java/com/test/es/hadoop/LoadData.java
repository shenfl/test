package com.test.es.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import java.io.IOException;

/**
 * https://blog.csdn.net/hellozhxy/article/details/80834695
 * https://github.com/sunnywalden/es-hadoop-data-share
 * /usr/hdp/2.6.2.0-205/hadoop/bin/yarn jar es-hadoop-1.0-SNAPSHOT.jar test_float1/test_float  hdfs://hadoop-3:8020/tmp/ngsearch5
 * /usr/hdp/2.6.2.0-205/hadoop/bin/yarn jar es-hadoop-1.0-SNAPSHOT.jar   hdfs://hadoop-3:8020/tmp/ngsearch5
 * yarn jar es-hadoop-1.0-SNAPSHOT.jar com.test.es.yarn.Client -jar_path /home/souche/projects/es-hadoop-1.0-SNAPSHOT.jar -jar_path_in_hdfs hdfs://hadoop-3:8020/tmp/es-hadoop-1.0-SNAPSHOT.jar -appname DemoApp -master_memory 128 -container_memory 256 -num_containers 3 -memory_overhead 256 -queue default -shell_args "abc 123"
 */
public class LoadData {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setBoolean("mapred.map.tasks.speculative.execution", false);
        conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
        conf.set("es.nodes", "172.17.40.233");
        conf.set("es.resource", "aa/a");
        conf.set("es.port", "9200");
        conf.set("es.http.timeout","100m");
        conf.set("es.input.json", "yes");
        conf.set("es.mapping.id", "id");
        Job job = Job.getInstance(conf, "write-es");
        job.setJarByClass(LoadData.class);
        job.setMapperClass(H2EMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputFormatClass(EsOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        System.out.println(job.waitForCompletion(true));
    }
}
