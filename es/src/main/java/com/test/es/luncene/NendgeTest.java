package com.test.es.luncene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class NendgeTest {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new NendgeAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("fff", "shenfl chenlu，hello中华人民13962196547共和国");
        tokenStream.reset();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }
        tokenStream.close();
        System.out.println("------");
        tokenStream = analyzer.tokenStream("sss", "接口可以定义静态方法，通过接口调用。实现类不需实现，也无法在实现类中直接调用。");
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }
    }
}
