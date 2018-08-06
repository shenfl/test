package com.test.arithmetic.graphic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2016/11/1.
 */
public abstract class AbstractGraph<V> implements Graph<V> {

    protected List<V> vertices;
    protected List<List<Integer>> neighbors;
    protected AbstractGraph(int[][] edges, V[] vertices) {
        this.vertices = new ArrayList<>();
        for (V vertice : vertices) {
            this.vertices.add(vertice);
        }
        createAdjencyList(edges, vertices.length);
    }
    protected AbstractGraph(List<Edge> edges, List<V> vertices) {
        this.vertices = vertices;
        createAdjencyList(edges, vertices.size());
    }

    private void createAdjencyList(List<Edge> edges, int size) {
        neighbors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            neighbors.add(new ArrayList<Integer>());
        }
        for (Edge edge : edges) {
            neighbors.get(edge.u).add(edge.v);
        }
    }

    protected void createAdjencyList(int[][] edges, int length) {
        neighbors = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            neighbors.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < edges.length; i++) {
            neighbors.get(edges[i][0]).add(edges[i][1]);
        }
    }

    @Override
    public int getSize() {
        return vertices.size();
    }

    @Override
    public List<V> getVertices() {
        return vertices;
    }

    @Override
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    @Override
    public List<Integer> getNeighbors(int index) {
        return neighbors.get(index);
    }

    @Override
    public int getDegree(int index) {
        return neighbors.get(index).size();
    }

    @Override
    public void printAdjacencyMetrix() {
        int[][] res = getAdjacencyMatrix();
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.println(res[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int[][] getAdjacencyMatrix() {
        int[][] res = new int[getSize()][getSize()];
        for (int i = 0; i < neighbors.size(); i++) {
            for (int j = 0; j < neighbors.get(i).size(); j++) {
                res[i][neighbors.get(i).get(j)] = 1;
            }
        }
        return res;
    }

    @Override
    public void printEdges() {
        for (int i = 0; i < neighbors.size(); i++) {
            System.out.print("第" + i + "个节点的邻接边：");
            for (int j = 0; j < neighbors.get(i).size(); j++) {
                System.out.print("(" + i + ", " + neighbors.get(i).get(j) + ")");
            }
            System.out.println();
        }
    }

    @Override
    public Tree dfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
        boolean[] isVisited= new boolean[vertices.size()];
        dfs(v, parent, searchOrder, isVisited);
        return new Tree(v, parent, searchOrder);
    }

    private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
        isVisited[v] = true;
        searchOrder.add(v);
        for (Integer i : neighbors.get(v)) {
            if (!isVisited[i]) {
                parent[i] = v;
                dfs(i, parent, searchOrder, isVisited);
            }
        }
    }

    @Override
    public Tree bfs(int v) {
        int[] parent = new int[vertices.size()];
        boolean[] isVisited = new boolean[vertices.size()];
        List<Integer> searchOrder = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(v);
        while (queue.size() > 0) {
            Integer poll = queue.poll();
            searchOrder.add(poll);
            for (Integer i : neighbors.get(poll)) {
                if (!isVisited[i]) {
                    queue.offer(i);
                    parent[i] = poll;
                    isVisited[i] = true;
                }
            }
        }
        return new Tree(v, parent, searchOrder);
    }

    public class Tree {
        private int root;
        private int[] parent;
        private List<Integer> searchOrder;
        public Tree(int v, int[] parent, List<Integer> searchOrder) {
            this.root = v;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }
        public int getRoot() {
            return root;
        }

        public int[] getParent() {
            return parent;
        }

        public List<Integer> getSearchOrder() {
            return searchOrder;
        }

        public List<V> getPath(int index) {
            List<V> path = new ArrayList<>();
            do {
                path.add(vertices.get(index));
                index = parent[index];
            } while (index != -1);
            return path;
        }
    }
    public static class Edge {
        public int u;
        public int v;
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }
}
