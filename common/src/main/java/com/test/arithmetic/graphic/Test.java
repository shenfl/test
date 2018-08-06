package com.test.arithmetic.graphic;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class Test {
    public static void main(String[] args) {
        String[] vertices={"seattle","san francisco","los angeles","denver",
                "kansas city","chicago","boston","new york","atlanta","miami","dallas","houston"};
        int[][] edges={{0,1},{0,3},{0,5},
        {1,0},{1,2},{1,3},
        {2,1},{2,3},{2,4},{2,10},
        {3,0},{3,1},{3,2},{3,4},{3,5},
        {4,2},{4,3},{4,5},{4,7},{4,8},{4,10},
        {5,0},{5,3},{5,4},{5,6},{5,7},
        {6,5},{6,7},
        {7,4},{7,5},{7,6},{7,8},
        {8,4},{8,7},{8,9},{8,10},{8,11},
        {9,8},{9,11},
        {10,2},{10,4},{10,8},{10,11},
        {11,8},{11,9},{11,10}};
        Graph<String> graph = new UnweightedGraph<>(edges, vertices);
        AbstractGraph.Tree dfs = graph.bfs(5);
        List searchOrder = dfs.getSearchOrder();
        System.out.println(searchOrder);
        System.out.println(dfs.getPath(5));
    }
}
