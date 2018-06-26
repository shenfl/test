package com.test.antlr.sandy;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class SandyParserTest {
    private static SandyParserLexer lexerForResource(String sourceName) throws IOException {
        return new SandyParserLexer(new ANTLRInputStream(SandyParserTest.class.getResourceAsStream(sourceName + ".sandy")));
    }
    private static SandyParserParser.SandyFileContext parseResource(String resourceName) throws IOException {
        return new SandyParserParser(new CommonTokenStream(lexerForResource(resourceName))).sandyFile();
    }

    public static void main(String[] args) throws IOException {
        parseResource("addition_assignment");
    }
}
