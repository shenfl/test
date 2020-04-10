package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2018/5/30
 */
public class TestIndex {
    public static void main(String[] args) throws IOException {


        Analyzer analyzer = new StandardAnalyzer();

        // Store the index in memory:
//        Directory directory = new RAMDirectory();
        Directory directory = FSDirectory.open(Paths.get("/Users/shenfl/IdeaProjects/test/common/data1"));
        // To store an index on disk, use this instead:
        //Directory directory = FSDirectory.open("/tmp/testindex");
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);

        //设置后不会合并成
        config.setUseCompoundFile(false);
        Document doc = new Document();
        String text = "This is the text to be text indexed.";
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        iwriter.addDocument(doc);
        iwriter.flush();

        text = "hello text";
        doc = new Document();
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        iwriter.addDocument(doc);

        iwriter.commit();


//        iwriter.deleteDocuments(new Term("fieldname", "hello"));
//        iwriter.commit();

        System.out.println(iwriter.numDocs());

        iwriter.close();



//文档一为：Students should be allowed to go out with their friends, but not allowed to drink beer.

//文档二为：My friend Jerry went to school to see his students but found them drunk which is not allowed.

//        writer.commit();//提交两篇文档，形成_0段。
//
//        writer.deleteDocuments(new Term("contents", "school"));//删除文档二
//        writer.commit();//提交删除，形成_0_1.del
//        indexDocs(writer, docDir);//再次索引两篇文档，Lucene不能判别文档与文档的不同，因而算两篇新的文档。
//        writer.commit();//提交两篇文档，形成_1段
//        writer.deleteDocuments(new Term("contents", "school"));//删除第二次添加的文档二
//        writer.close();//提交删除，形成_1_1.del
    }
}
