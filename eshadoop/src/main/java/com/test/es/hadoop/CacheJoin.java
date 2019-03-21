package com.test.es.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  yarn jar es-hadoop-1.0-SNAPSHOT.jar hdfs://hadoop-3:8020/tmp/input/order.txt hdfs://hadoop-3:8020/tmp/output3
 *  map端join实现
 */
public class CacheJoin {
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.addCacheFile(new URI("hdfs://hadoop-3:8020/tmp/input/product.txt"));
        job.setJarByClass(CacheJoin.class);

        job.setMapperClass(MyMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path (args[1]));
        job.waitForCompletion(true);
    }
    static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Map<String, String> map = new HashMap<>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            URI[] cacheFiles = context.getCacheFiles();
            System.out.println("------------");
            System.out.println(Arrays.toString(cacheFiles));
            Path path = new Path(cacheFiles[0].toString());
            FileSystem fileSystem = path.getFileSystem(context.getConfiguration());
            FSDataInputStream open = fileSystem.open(path);
            LineReader lineReader = new LineReader(open);
            Text value = new Text();
            while (lineReader.readLine(value) != 0) {
                String s = value.toString();
                int i = s.indexOf(",");
                map.put(s.substring(0, i).trim(), s.substring(i + 1));
                System.out.println(s);
            }
            System.out.println(map);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String text = value.toString();
            String[] split = text.split(",");
            System.out.println("+++++++++++");
            System.out.println(split[2]);
            System.out.println(map);
            String product = map.get(split[2]);
            context.write(new Text(split[0]), new Text(split[1] + ":" + product));
        }
    }
}
