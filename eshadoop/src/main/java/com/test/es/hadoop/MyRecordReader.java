package com.test.es.hadoop;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;

public class MyRecordReader extends RecordReader<LongWritable, Text> {

    //实际负责数据读入的RecordReader，注意这里的包路径为：org.apache.hadoop.util.LineReader;
    LineReader lineReader = null;

    // 分片数据在原始文件的起始位置
    private long start;
    // 分片数据在原始文件的结束位置
    private long end;

    // 记录行号，据此区分奇数偶数行
    private long line_number;

    // 解析出的key和value
    private LongWritable key = null;
    private Text value = null;

    // 文件输入流，从hdfs中读取文件
    FSDataInputStream fin = null;

    /**
     * 这个方法是每个分片执行一次？即使这些分片在一个mapper中
     * 用一个大文件跑，这个方法执行了两次，基本上前后执行，map任务还是只有一个
     * @param inputSplit
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext context) throws IOException, InterruptedException {
        // 获取文件分片
        FileSplit fileSplit = (FileSplit) inputSplit;
        // 得到分片数据在原始文件中的起终点位置
        start = fileSplit.getStart();
        end = start + fileSplit.getLength();
        // 分片对应的数据文件的路径
        Path filePath = fileSplit.getPath();
        // 获取相对应的文件系统对象
        FileSystem fileSystem = filePath.getFileSystem(context.getConfiguration());
        // 打开文件
        fin = fileSystem.open(filePath);
        // 调整读入的开始位置
        fin.seek(start);
        // lineReader负责从输入流读取数据
        lineReader = new LineReader(fin);
        // 设置起始行号
        line_number = 1;
        System.out.println(System.currentTimeMillis() + "My record reader " + start + ":" + end + ":" + this + ":" + inputSplit + ":" + inputSplit.toString());
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // 第一次开始读时，先初始化key和value
        if (key == null) {
            key = new LongWritable();
        }
        if (value == null) {
            value = new Text();
        }
        key.set(line_number);
        // 从文件中读取一行数据作为value，如果0==lineReader.readLine(value)，说明到达文件尾部，范围false
        if (0 == lineReader.readLine(value))
            return false;
        // 调整行号
        line_number++;

        // 未到文件尾
        return true;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        fin.close();
    }
}
