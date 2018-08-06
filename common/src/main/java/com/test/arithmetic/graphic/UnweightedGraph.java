package com.test.arithmetic.graphic;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class UnweightedGraph<V> extends AbstractGraph<V> {

    protected UnweightedGraph(int[][] edges, V[] vertices) {
        super(edges, vertices);
    }
    protected UnweightedGraph(List<Edge> edges, List<V> vertices) {
        super(edges, vertices);
    }
}
