package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2018/4/19
 */
public class TestLucene {
    public static void main(String[] args) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();

        // Store the index in memory:
//        Directory directory = new RAMDirectory();
        Directory directory = FSDirectory.open(Paths.get("/Users/dasouche1/IdeaProjects/test/common/data"));
        // To store an index on disk, use this instead:
        //Directory directory = FSDirectory.open("/tmp/testindex");
//        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//        config.setUseCompoundFile(false);
//        IndexWriter iwriter = new IndexWriter(directory, config);
//        Document doc = new Document();
//        String text = "This is the text to be text indexed.";
//        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
//        iwriter.addDocument(doc);
//
//        text = "hello text";
//        doc = new Document();
//        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
//        iwriter.addDocument(doc);
//        iwriter.commit();
//        iwriter.deleteDocuments(new Term("fieldname", "hello"));
//        iwriter.commit();
//        iwriter.close();

        // Now search the index:
        DirectoryReader ireader = DirectoryReader.open(directory);
        System.out.println(ireader.maxDoc());
        System.out.println(ireader.numDocs());
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("fieldname", analyzer);
        Query query = parser.parse("text");
        ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
        System.out.println(hits.length);
        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("fieldname"));
        }
        ireader.close();
        directory.close();
    }
}
