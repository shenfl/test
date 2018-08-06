package com.test.arithmetic.graphic;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public interface Graph<V> {
    int getSize();
    List<V> getVertices();
    V getVertex(int index);
    int getIndex(V v);
    List<Integer> getNeighbors(int index);
    int getDegree(int index);
    int[][] getAdjacencyMatrix();
    void printAdjacencyMetrix();
    void printEdges();
    AbstractGraph.Tree dfs(int v);
    AbstractGraph.Tree bfs(int v);
}
