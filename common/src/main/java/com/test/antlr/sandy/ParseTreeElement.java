package com.test.antlr.sandy;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public abstract class ParseTreeElement {
    abstract String multiLineString(String indentation);
    static class ParseTreeLeaf extends ParseTreeElement {

        public ParseTreeLeaf(String text) {
            this.text = text;
        }
        private String text;

        @Override
        String multiLineString(String indentation) {
            return indentation + "T[" + text + "]\n";
        }
    }
    static class ParseTreeNode extends ParseTreeElement {
        public ParseTreeNode(String name) {
            this.name = name;
        }
        private String name;
        private List<ParseTreeElement> children;
        @Override
        String multiLineString(String indentation) {
            StringBuilder sb = new StringBuilder();
            sb.append(indentation).append(name).append("\n");
            for (ParseTreeElement child : children) {
                sb.append(child.multiLineString(indentation + "\t"));
            }
            return sb.toString();
        }
    }
    public static ParseTreeNode toParserTree(ParserRuleContext node) {
        String res = node.getClass().getSimpleName();
        return null;
    }
}
