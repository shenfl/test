package com.test.es.hadoop;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 二次排序
 * https://github.com/qiushangwenyue/HadoopDemo/blob/master/src/main/java/cn/itcast/bigdata/mr/secondarysort/SecondarySort.java
 * Order_0000001,Pdt_01,222.8
 * Order_0000001,Pdt_05,25.8
 * Order_0000002,Pdt_05,325.8
 * Order_0000002,Pdt_03,522.8
 * Order_0000002,Pdt_04,122.4
 * Order_0000003,Pdt_01,222.8
 * Order_0000003,Pdt_01,322.8
 * 也就是说设置setGroupingComparatorClass后，就是根据这个做reduce分组，分完组后还是需要排序的，排序默认是根据对象的hashcode方法，这样就把
 * amount最高的放到前面了，而reduce只放进去一个key，所以就是那个amount最高的key
 * 因为同一个reducer端会有很多不同的key过来，我猜测这些key会被排序，排序后做grouping，这样itemid相同的被放到一个组里，同样这个组里的key中也是安装amount排序的
 * 也可以参照https://blog.csdn.net/amuseme_lu/article/details/6956171
 */
public class SecondarySort {

    static class SecondarySortMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable>{

        OrderBean bean = new OrderBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            String[] fields = StringUtils.split(line, ",");

            bean.set(new Text(fields[0]), new DoubleWritable(Double.parseDouble(fields[2])));

            context.write(bean, NullWritable.get());

        }
    }

    static class SecondarySortReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable>{


        //到达reduce时，相同id的所有bean已经被看成一组，且金额最大的那个一排在第一位
        @Override
        protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }


    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SecondarySort.class);

        job.setMapperClass(SecondarySortMapper.class);
        job.setReducerClass(SecondarySortReducer.class);


        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //在此设置自定义的Groupingcomparator类
        job.setGroupingComparatorClass(ItemidGroupingComparator.class);
        //在此设置自定义的partitioner类
        job.setPartitionerClass(ItemIdPartitioner.class);

        //设置两个reduce task，则会生成两个结果文件
        job.setNumReduceTasks(2);

        job.waitForCompletion(true);

    }
}
