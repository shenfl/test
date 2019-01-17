package com.test.antlr.rows;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;

public class Rows {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream("/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/rows/file.txt"));
        RowsLexer lexer = new RowsLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RowsParser parser = new RowsParser(tokens, 1);    // pass column number!
        parser.setBuildParseTree(false);    // don't waste time bulding a tree
//        parser.file();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new RowsBaseListener(), parser.file());
    }
}
