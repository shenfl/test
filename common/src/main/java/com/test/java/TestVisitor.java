package com.test.java;

/**
 * 访问者模式
 * https://www.cnblogs.com/java-my-life/archive/2012/06/14/2545381.html
 */
public class TestVisitor {
    public static void main(String[] args) {
        // 这种方式是不对的
//        Visitor visitor = new VisitorB();
//        Node node = new BNode();
//        visitor.visit(node);// 报错

        Visitor visitor = new VisitorB();
        Node node = new BNode();
        node.accept(visitor);

        /**
         * 其实完全可以使用另外一种方式，Visitor接口还是一样，
         * 当Node有数据后，用if语句判断具体的Node类型
         * 然后强转后传入Visitor接口
         */
    }
    interface Visitor{
        void visit(ANode node);
        void visit(BNode node);
    }
    static class VisitorA implements Visitor {

        @Override
        public void visit(ANode node) {
            System.out.println(node.operationA());
            System.out.println("va");
        }

        @Override
        public void visit(BNode node) {
            System.out.println(node.operationB());
            System.out.println("va");
        }
    }
    static class VisitorB implements Visitor {

        @Override
        public void visit(ANode node) {
            System.out.println(node.operationA());
            System.out.println("vb");
        }

        @Override
        public void visit(BNode node) {
            System.out.println(node.operationB());
            System.out.println("vb");
        }
    }
    static abstract class Node {
        /**
         * 接受操作
         */
        public abstract void accept(Visitor visitor);
    }
    static class ANode extends Node {
        public String operationA(){
            return "NodeA";
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }
    static class BNode extends Node {
        public String operationB(){
            return "NodeB";
        }
        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }
}
