package com.test.es.hadoop;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartitioner extends Partitioner<LongWritable, Text> {

    @Override
    public int getPartition(LongWritable key, Text value, int numPartitions) {
    /*
    这里的处理逻辑为：
    如果为偶数行，则调整其key值为0，设置其分区编号为0
    如果为奇数行，则调整其key值为1，设置其分区编号为1
    对性质相同的列，调整其key值相同，以便进行combine等操作
    */
        if (key.get() % 2 == 0) {
            //调整key值
            key.set(0);
            return 0;
        }

        key.set(1);
        return 1;
    }
}