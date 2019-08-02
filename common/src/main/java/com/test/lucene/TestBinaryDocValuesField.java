package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.BinaryDocValuesField;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TestBinaryDocValuesField {
    private Directory directory;

    {
        try {
            FileOperation.deleteFile("./data");
            directory = new MMapDirectory(Paths.get("./data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Analyzer analyzer = new WhitespaceAnalyzer();
    private IndexWriterConfig conf = new IndexWriterConfig(analyzer);
    private IndexWriter indexWriter;
    public static void main(String[] args) throws Exception{
        TestBinaryDocValuesField test = new TestBinaryDocValuesField();
        test.test();
    }

    private void test() throws IOException {
        conf.setUseCompoundFile(false);
        indexWriter = new IndexWriter(directory, conf);
        Document doc = new Document();
        byte[] bytes = new byte[4];
        BytesRef data = new BytesRef(bytes);
        BinaryDocValuesField field = new BinaryDocValuesField("dv", data);
        doc.add(field);
        for (int i = 0; i < 1000; i++) {
            bytes[0] = (byte)(i >> 24);
            bytes[0] = (byte)(i >> 16);
            bytes[0] = (byte)(i >> 8);
            bytes[0] = (byte)i;
            indexWriter.addDocument(doc);
        }
        indexWriter.commit();

        DirectoryReader reader = DirectoryReader.open(indexWriter);
        List<LeafReaderContext> leaves = reader.leaves();
        System.out.println(leaves.size());

        for (LeafReaderContext leaf : reader.leaves()) {
            LeafReader reader1 = leaf.reader();
            BinaryDocValues dv = reader1.getBinaryDocValues("dv");
            for (int i = 0; i < reader1.maxDoc(); i++) {
                bytes[0] = (byte)(i >> 24);
                bytes[0] = (byte)(i >> 16);
                bytes[0] = (byte)(i >> 8);
                bytes[0] = (byte)i;
                int i1 = dv.nextDoc();
                System.out.println(i1);
                BytesRef bytesRef = dv.binaryValue();
                System.out.println(bytesRef.equals(data));
            }
        }
    }
}
