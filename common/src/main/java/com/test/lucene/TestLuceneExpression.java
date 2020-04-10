package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.expressions.Expression;
import org.apache.lucene.expressions.SimpleBindings;
import org.apache.lucene.expressions.js.JavascriptCompiler;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2018/9/15
 * https://blog.csdn.net/wuyinggui10000/article/details/46315989
 * 表达式计算分数
 */
public class TestLuceneExpression {
    public static final String PATH = "/Users/shenfl/IdeaProjects/test/common/data2";
    public static void main(String[] args) throws IOException {
//        index();
        search();
//        testAcreage();
//        testCircum();
    }
    public static void search() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(PATH));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new TermQuery(new Term("width", "3"));
        TopDocs hits = searcher.search(query,10);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println(doc.get("width"));
        }
        reader.close();
    }
    public static void index(){
        try {
            Directory directory = FSDirectory.open(Paths.get(PATH));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter writer = new IndexWriter(directory, config);

            Document doc = new Document();
            //模拟长方形
            doc.add(new StoredField("width", 3));
            doc.add(new StoredField("longth", 4));
            doc.add(new NumericDocValuesField("width", 3));
            doc.add(new NumericDocValuesField("longth", 4));
            writer.addDocument(doc);

            Document doc1 = new Document();
            doc1.add(new StoredField("width", 2));
            doc1.add(new StoredField("longth", 5));
            doc1.add(new NumericDocValuesField("width", 2));
            doc1.add(new NumericDocValuesField("longth", 5));
            writer.addDocument(doc1);

            Document doc2 = new Document();
            doc2.add(new StoredField("width", 2));
            doc2.add(new StoredField("longth", 6));
            doc2.add(new NumericDocValuesField("width", 2));
            doc2.add(new NumericDocValuesField("longth", 6));
            writer.addDocument(doc2);

            writer.commit();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 面积比较
     */
    public static void testAcreage(){
        try {
            Expression expr = JavascriptCompiler.compile("width*longth");

            SimpleBindings bindings = new SimpleBindings();
            bindings.add(new SortField("width", SortField.Type.INT));
            bindings.add(new SortField("longth", SortField.Type.INT));

            Sort sort = new Sort(expr.getSortField(bindings, true));
            Query query = new MatchAllDocsQuery();

            Directory directory = FSDirectory.open(Paths.get(PATH));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, 10, sort);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                System.out.println(searcher.doc(scoreDoc.doc).get("longth"));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * 周长比较
     */
    public static void testCircum(){
        try {
            Expression expr = JavascriptCompiler.compile("width+longth+sqrt(pow(width,2)+pow(longth,2))");

            SimpleBindings bindings = new SimpleBindings();
            bindings.add(new SortField("width", SortField.Type.INT));
            bindings.add(new SortField("longth", SortField.Type.INT));

            Sort sort = new Sort(expr.getSortField(bindings, true));
            Query query = new MatchAllDocsQuery();

            Directory directory = FSDirectory.open(Paths.get(PATH));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, 10, sort);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                System.out.println(searcher.doc(scoreDoc.doc));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}
