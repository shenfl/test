package com.test.es.hadoop;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

class H2EMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    @Override
    public void run(Context context) throws IOException, InterruptedException {
        super.run(context);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String s = value.toString();
        s = s.substring(s.indexOf("{"));
        context.write(NullWritable.get(), new Text(s));
    }

    @Override
    protected void cleanup(Context context) throws IOException,InterruptedException {
        super.cleanup(context);
    }

}