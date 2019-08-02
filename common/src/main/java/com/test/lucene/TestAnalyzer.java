package com.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.CharsRefBuilder;

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


        analyzer = new SynonymAnalyzer(1, 1);
        TokenStream tokenStream = analyzer.tokenStream("", "ab hello world");
        try {
            tokenStream.reset();
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            while (tokenStream.incrementToken()) {
                System.out.println(charTermAttribute.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                tokenStream.close();
            } catch (IOException e) {
            }
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

    static class SynonymAnalyzer extends Analyzer {

        private int minGram;
        private int maxGram;

        public SynonymAnalyzer(int minGram, int maxGram) {
            this.minGram = minGram;
            this.maxGram = maxGram;
            validate();
        }

        private void validate() {
            assert minGram > 0;
            assert maxGram >= minGram;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            Tokenizer tokenizer = new StandardTokenizer();
            SynonymMap.Builder b = new SynonymMap.Builder(true);
            CharsRefBuilder inputCharsRef = new CharsRefBuilder();
            SynonymMap.Builder.join(new String[]{"ab"}, inputCharsRef);

            CharsRefBuilder outputCharsRef = new CharsRefBuilder();
            SynonymMap.Builder.join(new String[]{"cd"}, outputCharsRef);

            b.add(inputCharsRef.get(), outputCharsRef.get(), true);
            try {
                return new TokenStreamComponents(tokenizer, new SynonymGraphFilter(tokenizer, b.build(), true));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
