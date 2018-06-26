package com.test.antlr.idata;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.InputStream;

public class IData {
    public static void main(String[] args) throws Exception {
        InputStream is = args.length > 0 ? new FileInputStream(args[0]) : System.in;

        ANTLRInputStream input = new ANTLRInputStream(is);
        IDataLexer lexer = new IDataLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        IDataParser parser = new IDataParser(tokens);
        ParseTree tree = parser.file();

        RewriteListener listener = new RewriteListener(tokens);

        System.out.println("Before Rewriting");
        System.out.println(listener.rewriter.getText());

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        System.out.println("After Rewriting");
        System.out.println(listener.rewriter.getText());
    }
}
