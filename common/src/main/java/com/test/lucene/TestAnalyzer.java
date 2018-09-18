package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 * Created by shenfl on 2018/9/13
 */
public class TestAnalyzer {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new MyAnalyzer();
        TokenStream stream = analyzer.tokenStream("", "hello world");
        stream.reset();
        CharTermAttribute termAttribute = stream.addAttribute(CharTermAttribute.class);
        while (stream.incrementToken()) {
            System.out.println(termAttribute.toString());
        }
        stream.close(); // 下一次tokenStream之前需要把上次的流close掉
        stream = analyzer.tokenStream("", "will do");
        stream.reset();
        termAttribute = stream.addAttribute(CharTermAttribute.class);
        while (stream.incrementToken()) {
            System.out.println(termAttribute.toString());
        }
    }
    static class MyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            Tokenizer tokenizer = new StandardTokenizer();
//            return new TokenStreamComponents(tokenizer, new EdgeNGramTokenFilter(tokenizer, 2, 4));
            return new TokenStreamComponents(tokenizer, new PostEdgeNGramTokenFilter(tokenizer, 2, 4));
        }
    }
}
