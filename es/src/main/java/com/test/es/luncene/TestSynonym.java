package com.test.es.luncene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.io.StringReader;

public class TestSynonym {
    public static void main(String[] args) throws IOException {
        SynonymAnalyzer analyzer = new SynonymAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader("aa quick brown fox"));
        tokenStream.reset();

        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        PositionIncrementAttribute positionIncrementAttribute =
                tokenStream.addAttribute(PositionIncrementAttribute.class);
        TypeAttribute typeAttribute = tokenStream.addAttribute(TypeAttribute.class);

        int position = 0;
        while (tokenStream.incrementToken()) {
            int positionIncrement = positionIncrementAttribute.getPositionIncrement();
//            if (positionIncrement > 0) {
//                position += positionIncrement;
//                System.out.println();
//                System.out.print(position + " : ");
//            }
            position += positionIncrement;
            System.out.println();
            System.out.print(position + " : ");

            System.out.printf("[%s : %d ->  %d : %s]", charTermAttribute.toString(), offsetAttribute.startOffset(), offsetAttribute.endOffset(),
                    typeAttribute.type());
            System.out.println();
        }
    }
}
