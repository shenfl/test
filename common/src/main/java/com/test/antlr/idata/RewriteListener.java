package com.test.antlr.idata;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class RewriteListener extends IDataBaseListener {
    TokenStreamRewriter rewriter;

    public RewriteListener(TokenStream tokens) {
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public void enterGroup(IDataParser.GroupContext ctx) {
        rewriter.insertAfter(ctx.stop, '\n');
    }
}
