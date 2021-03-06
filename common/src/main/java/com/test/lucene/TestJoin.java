package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.join.JoinUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

/**
 * Created by shenfl on 2018/9/16
 * https://blog.csdn.net/wuyinggui10000/article/details/46336417
 * 测试join功能
 */
public class TestJoin {
    public static void main(String[] args) throws Exception {
        testSimple();
    }
    public static void testSimple() throws Exception {
        final String idField = "id";
        final String toField = "productId";

        Directory dir = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter w = new IndexWriter(dir, config);

        // 0
        Document doc = new Document();
        doc.add(new TextField("description", "random text", Field.Store.YES));
        doc.add(new TextField("name", "name1", Field.Store.YES));
        doc.add(new TextField(idField, "1", Field.Store.YES));
        doc.add(new SortedDocValuesField(idField, new BytesRef("1")));

        w.addDocument(doc);

        // 1
        Document doc1 = new Document();
        doc1.add(new TextField("price", "10.0", Field.Store.YES));
        doc1.add(new TextField(idField, "2", Field.Store.YES));
        doc1.add(new SortedDocValuesField(idField, new BytesRef("2")));
        doc1.add(new TextField(toField, "1", Field.Store.YES));
        doc1.add(new SortedDocValuesField(toField, new BytesRef("1")));

        w.addDocument(doc1);

        // 2
        Document doc2 = new Document();
        doc2.add(new TextField("price", "20.0", Field.Store.YES));
        doc2.add(new TextField(idField, "3", Field.Store.YES));
        doc2.add(new SortedDocValuesField(idField, new BytesRef("3")));
        doc2.add(new TextField(toField, "1", Field.Store.YES));
        doc2.add(new SortedDocValuesField(toField, new BytesRef("1")));

        w.addDocument(doc2);

        // 3
        Document doc3 = new Document();
        doc3.add(new TextField("description", "more random text", Field.Store.YES));
        doc3.add(new TextField("name", "name2", Field.Store.YES));
        doc3.add(new TextField(idField, "4", Field.Store.YES));
        doc3.add(new SortedDocValuesField(idField, new BytesRef("4")));

        w.addDocument(doc3);


        // 4
        Document doc4 = new Document();
        doc4.add(new TextField("price", "10.0", Field.Store.YES));
        doc4.add(new TextField(idField, "5", Field.Store.YES));
        doc4.add(new SortedDocValuesField(idField, new BytesRef("5")));
        doc4.add(new TextField(toField, "4", Field.Store.YES));
        doc4.add(new SortedDocValuesField(toField, new BytesRef("4")));
        w.addDocument(doc4);

        // 5
        Document doc5 = new Document();
        doc5.add(new TextField("price", "20.0", Field.Store.YES));
        doc5.add(new TextField(idField, "6", Field.Store.YES));
        doc5.add(new SortedDocValuesField(idField, new BytesRef("6")));
        doc5.add(new TextField(toField, "4", Field.Store.YES));
        doc5.add(new SortedDocValuesField(toField, new BytesRef("4")));
        w.addDocument(doc5);

        //6
        Document doc6 = new Document();
        doc6.add(new TextField(toField, "4", Field.Store.YES));
        doc6.add(new SortedDocValuesField(toField, new BytesRef("4")));
        w.addDocument(doc6);
        w.commit();
        w.close();
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(reader);


        // Search for product
        Query joinQuery = JoinUtil.createJoinQuery(idField, false, toField, new TermQuery(new Term("name", "name2")), indexSearcher, ScoreMode.None);
        System.out.println(joinQuery);
        TopDocs result = indexSearcher.search(joinQuery, 10);
        System.out.println("查询到的匹配数据："+result.totalHits);


        joinQuery = JoinUtil.createJoinQuery(idField, false, toField, new TermQuery(new Term("name", "name1")), indexSearcher, ScoreMode.None);
        result = indexSearcher.search(joinQuery, 10);
        System.out.println("查询到的匹配数据："+result.totalHits);
        // Search for offer
        joinQuery = JoinUtil.createJoinQuery(toField, false, idField, new TermQuery(new Term("id", "5")), indexSearcher, ScoreMode.None);
        result = indexSearcher.search(joinQuery, 10);
        System.out.println("查询到的匹配数据："+result.totalHits);

        indexSearcher.getIndexReader().close();
        dir.close();
    }
}
