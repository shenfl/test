package com.test;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by shenfl on 2018/5/28
 */
public class Test {
    public static void main(String[] args) throws IOException {
        IKSegmenter segmenter = new IKSegmenter(new StringReader(""), true);
//        segmenter.reset(new StringReader("人库及"));
        segmenter.reset(new StringReader("kasdfe1213mnr"));
        Lexeme lex = null;
        while((lex = segmenter.next()) != null){
            System.out.println(lex.getLexemeText());
        }
    }
}
