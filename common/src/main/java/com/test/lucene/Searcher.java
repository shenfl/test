package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2018/5/30
 */
public class Searcher {

    public static void search(String indexDir, String q) throws Exception {
        // 得到读取索引文件的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        // 通过Dir得到路径下所有文件
        IndexReader reader = DirectoryReader.open(dir);
        // 建立索引查询器
        IndexSearcher searcher = new IndexSearcher(reader);
        // 实例化分析器
        Analyzer analyzer = new StandardAnalyzer();

        /********建立查询解析器********/

        // 第一个参数是要查询的字段； 第二个参数市分析器Analyzer
        QueryParser parser = new QueryParser("contents", analyzer);
        // 根据传进来的q查找
        Query query = parser.parse(q);
        // 计算索引开始时间
        long start = System.currentTimeMillis();

        /********开始查询********/

        // 第一个参数是通过传过来的参数来查找得到的query； 第二个参数是要查询出的行数
        TopDocs hits = searcher.search(query,10);
        // 计算索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("匹配 "+ q + "，查询到 " + hits.totalHits + " 个记录， 用时：" + (end - start));

        // 遍历hits.scoreDocs，得到scoreDoc
        // scoreDoc：得分文档，即得到的文档  scoreDocs：代表topDocs这个文档数组
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println(doc.get("fullPath"));
        }
        //关闭reader
        reader.close();
    }
}