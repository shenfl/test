package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2020-03-18
 */
public class TermQueryTest {

    private Directory directory;

    {
        try {
            // 每次运行demo先清空索引目录中的索引文件
            FileOperation.deleteFile("./data");
            directory = new MMapDirectory(Paths.get("./data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 空格分词器
    private Analyzer analyzer = new WhitespaceAnalyzer();
    private IndexWriterConfig conf = new IndexWriterConfig(analyzer);

    private void doDemo() throws Exception {
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        Document doc ;
        // 0
        doc = new Document();
        doc.add(new TextField("content", "h", Field.Store.YES));
        doc.add(new TextField("author", "author1", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 1
        doc = new Document();
        doc.add(new TextField("content", "b", Field.Store.YES));
        doc.add(new TextField("author", "author2", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 2
        doc = new Document();
        doc.add(new TextField("content", "a c", Field.Store.YES));
        doc.add(new TextField("author", "author3", Field.Store.YES));

        indexWriter.addDocument(doc);
        // 3
        doc = new Document();
        doc.add(new TextField("content", "a c e", Field.Store.YES));
        doc.add(new TextField("author", "author4", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 4
        doc = new Document();
        doc.add(new TextField("content", "h", Field.Store.YES));
        doc.add(new TextField("author", "author5", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 5
        doc = new Document();
        doc.add(new TextField("content", "c e", Field.Store.YES));
        doc.add(new TextField("author", "author6", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 6
        doc = new Document();
        doc.add(new TextField("content", "c a e", Field.Store.YES));
        doc.add(new TextField("author", "author7", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 7
        doc = new Document();
        doc.add(new TextField("content", "f", Field.Store.YES));
        doc.add(new TextField("author", "author8", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 8
        doc = new Document();
        doc.add(new TextField("content", "b c d e c e", Field.Store.YES));
        doc.add(new TextField("author", "author9", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 9
        doc = new Document();
        doc.add(new TextField("content", "a c e a b c", Field.Store.YES));
        doc.add(new TextField("author", "author10", Field.Store.YES));
        indexWriter.addDocument(doc);
        // 索引阶段结束


        Document d = new Document();
        d.add(new StringField("newField", "newFieldValue", Field.Store.YES));
        indexWriter.updateDocument(new Term("content", "a"), d);
        indexWriter.commit();

        //查询阶段
        IndexReader reader = DirectoryReader.open(indexWriter);
        IndexSearcher searcher = new IndexSearcher(reader);

        // 查询条件, 找出 域名为"content", 域值中包含"a"的文档（Document）
        Query query = new TermQuery(new Term("content", "a"));

        // 返回Top5的结果
        int resultTopN = 5;

        ScoreDoc[] scoreDocs = searcher.search(query, resultTopN).scoreDocs;

        System.out.println("Total Result Number: "+scoreDocs.length+"");
        for (int i = 0; i < scoreDocs.length; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            // 输出满足查询条件的 文档号
            System.out.println("result"+i+": 文档"+scoreDoc.doc+"");
            Document document = searcher.doc(scoreDoc.doc);
            for (IndexableField field : document) {
                System.out.println("\t" + field.name() + ": " + document.get(field.name()));
            }
        }

    }

    public static void main(String[] args) throws Exception{
        TermQueryTest termQueryTest = new TermQueryTest();
        termQueryTest.doDemo();
    }
}