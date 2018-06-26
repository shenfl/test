package com.test.antlr.sandy;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class TestLexer {
    public static void main(String[] args) throws IOException {
        System.out.println(tokens(lexerForCode("var a = 1")));
        System.out.println(tokens(lexerForCode("var a = 1.23")));
        System.out.println(tokens(lexerForCode("var a = 1 + 2")));
        System.out.println(tokens(lexerForCode("1 + a * 3 / 4 - 5")));
        System.out.println(tokens(lexerForCode("1 + (a * 3) - 5.12")));
    }

    private static SandyParserLexer lexerForCode(String code) throws IOException {
        return new SandyParserLexer(new ANTLRInputStream(new StringReader(code)));
    }

    private static List<String> tokens(SandyParserLexer sandyLexer) {
        List<String> tokens = new LinkedList<>();
        Token token = null;
        do {
            token = sandyLexer.nextToken();
            if (token.getType() == -1) tokens.add("EOF");
            else if (token.getType() != SandyParserLexer.WS) {
                tokens.add(sandyLexer.getRuleNames()[token.getType() - 1]);
            }
        } while (token.getType() != -1);
        return tokens;
    }
}
