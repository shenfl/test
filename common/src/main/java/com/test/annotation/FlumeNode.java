package com.test.annotation;

/**
 * Created by Administrator on 2015/12/14.
 */
@NodeType("flume")
public class FlumeNode implements Node {
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
