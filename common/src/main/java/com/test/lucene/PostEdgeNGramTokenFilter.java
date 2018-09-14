package com.test.lucene;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;

/**
 * Created by shenfl on 2018/9/13
 */
public class PostEdgeNGramTokenFilter extends TokenFilter {

    private int minGram;
    private int maxGram;
    private char[] curTermBuffer;
    private int savePosIncr;
    private State state;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    private int curTermLength;
    private int curCodePointCount;
    private int curGramSize;

    protected PostEdgeNGramTokenFilter(TokenStream input, int minGram, int maxGram) {
        super(input);
        this.minGram = minGram;
        this.maxGram = maxGram;
    }

    @Override
    public boolean incrementToken() throws IOException {
        while (true) {
            if (curTermBuffer == null) {
                if (!input.incrementToken()) {
                    return false;
                } else {
                    curTermBuffer = termAtt.buffer().clone();
                    curTermLength = termAtt.length();
                    curCodePointCount = Character.codePointCount(termAtt, 0, termAtt.length());
                    curGramSize = minGram;
                    state = captureState();
                    savePosIncr += posIncrAtt.getPositionIncrement();
                }
            }
            if (curGramSize <= maxGram) {         // if we have hit the end of our n-gram size range, quit
                if (curGramSize <= curCodePointCount) { // if the remaining input is too short, we can't generate any n-grams
                    // grab gramSize chars from front or back
                    restoreState(state);
                    // first ngram gets increment, others don't
                    if (curGramSize == minGram) {
                        posIncrAtt.setPositionIncrement(savePosIncr);
                        savePosIncr = 0;
                    } else {
                        posIncrAtt.setPositionIncrement(0);
                    }
                    final int charLength = Character.offsetByCodePoints(curTermBuffer, 0, curTermLength, 0, curGramSize);
                    termAtt.copyBuffer(curTermBuffer, curTermLength - charLength, charLength);
                    curGramSize++;
                    return true;
                }
            }
            curTermBuffer = null;
        }
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        curTermBuffer = null;
        savePosIncr = 0;
    }
}
