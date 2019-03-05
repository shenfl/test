package com.test.es.hadoop;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.elasticsearch.hadoop.mr.LinkedMapWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class E2HMapper extends Mapper<Text, Text, Text, Text> {

    private static final Logger LOG = LoggerFactory.getLogger(E2HMapper.class);

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    @Override
    protected void map(Text key, Text value, Context context)
            throws IOException, InterruptedException {
        LOG.info("key {} value {}", key, value);
        context.write(key, value);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
    }

}