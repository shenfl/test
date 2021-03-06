package com.test.arithmetic;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by shenfl on 2018/6/11
 */
public class TestArithmetic {
    /**
     * 动态规划找相同最长子序列
     */
    @org.junit.Test
    public void test1() {
        char[] a = {'s', 'e', 'y', 'w', 'g'};
        char[] b = {'q', 's', 'w', 'a', 'g', 'a'};

        int[][] arr = new int[a.length + 1][b.length + 1];
        // a[i] == b[j]相当与动态规划数组中的第二行，用来确定arr[i + 1][j + 1]的值是没有问题的
        // 而且for中遍历的是a和b数组，不是arr数组
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j]) {
                    arr[i + 1][j + 1] = Math.max(Math.max(arr[i][j] + 1, arr[i + 1][j]), arr[i][j + 1]);
                } else {
                    arr[i + 1][j + 1] = Math.max(Math.max(arr[i][j], arr[i + 1][j]), arr[i][j + 1]);
                }
            }
        }
        System.out.println(arr[a.length][b.length]);
    }

    /**
     * 编辑距离，递归实现
     * https://www.jianshu.com/p/a617d20162cf
     */
    @org.junit.Test
    public void test2() {
        System.out.println(distance("xyz", "xxc"));
        System.out.println(distance(new char[]{'x','y','z'},new char[]{'x','x','c'}));
    }

    private int distance(char[] a, char[] b) {
        int[][] arr = new int[a.length + 1][b.length + 1];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j]) {
                    arr[i+1][j+1] = Math.min(Math.min(arr[i][j], arr[i+1][j] + 1), arr[i][j+1] + 1);
                } else {
                    arr[i+1][j+1] = Math.min(Math.min(arr[i][j] + 1, arr[i+1][j] + 1), arr[i][j+1] + 1);
                }
            }
        }
        return arr[a.length][b.length];
    }

    private int distance(String s1, String s2) {
        if (s1.length() == 0) return s2.length();
        if (s2.length() == 0) return s1.length();
        if (s1.equals(s2)) return 0;
        int temp;
        if (s1.charAt(s1.length() - 1) == s2.charAt(s2.length() - 1)) temp = 0;
        else temp = 1;
        return Math.min(Math.min(distance(s1.substring(0, s1.length() - 1), s2) + 1, distance(s2.substring(0, s2.length() - 1), s1) + 1), distance(s1.substring(0, s1.length() - 1), s2.substring(0, s2.length() - 1)) + temp);
    }

    /**
     * 计算一个数组中第k大值，O(n)
     */
    @org.junit.Test
    public void test3() {
        int[] array = new int[]{5, 7, 2, 9, 4, 11, 3, 8, 6, 1, 0, 17};
        int k = 3;
        System.out.println(work(array, 0, array.length - 1, k));
    }

    private int work(int[] array, int start, int end, int k) {
        if (k > end - start + 1) {
            return -1;
        } else if (k == 1) {
            int max = Integer.MIN_VALUE;
            for (int i = start; i <= end; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            return max;
        } else if (k == end - start + 1) {
            int min = Integer.MAX_VALUE;
            for (int i = start; i <= end; i++) {
                if (array[i] < min) {
                    min = array[i];
                }
            }
            return min;
        }
        Random random = new Random();
        int i = random.nextInt(end - start + 1);
        int temp = array[i];
        array[i] = array[end];
        array[end] = temp;
        int maxScale = -1;
        for (int j = start; j < end; j++) {
            if (array[j] <= temp) {
                continue;
            } else {
                int t = array[j];
                array[j] = array[maxScale + 1];
                array[maxScale + 1] = t;
                maxScale++;
            }
        }
        int t = array[end];
        array[end] = array[maxScale + 1];
        array[maxScale + 1] = t;
        if (maxScale + 1 >= k) {
            return work(array, start, start + maxScale, k);
        } else {
            return work(array, start + maxScale + 1, end, k - maxScale - 1);
        }
    }

    @Test
    /**
     * 力扣 31
     */
    public void nextPermutation() {
        int[] nums = new int[2];
        boolean flag = false;
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                int temp = i;
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] > nums[i - 1] && nums[j] < nums[temp]) {
                        temp = j;
                    }
                }
                swap(nums, i - 1, temp);
                flag = true;
                sort(nums, i);
                break;
            }
        }
        if (!flag) {
            small(nums, 0);
        }
    }
    private void sort(int[] nums, int i) {
        int last = nums.length - 1;
        while (i < last) {
            for (int j = i; j < last; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
            last--;
        }
    }
    private void small(int[] nums, int i) {
        int last = nums.length - 1;
        while (i < last) {
            swap(nums, i, last);
            i++;
            last--;
        }
    }
    private void swap(int[] nums, int i, int j) {
        int temp;
        temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 拓扑排序
     * https://time.geekbang.org/column/article/76207
     */
    @org.junit.Test
    public void test4() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.topoSortByKahn();
    }
    static class Graph {
        private int v; // 顶点的个数
        private LinkedList<Integer> adj[]; // 邻接表
        public Graph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i=0; i<v; ++i) {
                adj[i] = new LinkedList<>();
            }
        }
        public void addEdge(int s, int t) { // s先于t，边s->t
            adj[s].add(t);
        }
        public void topoSortByKahn() {
            int[] inDegree = new int[v]; // 统计每个顶点的入度
            for (int i = 0; i < v; ++i) {
                for (int j = 0; j < adj[i].size(); ++j) {
                    int w = adj[i].get(j); // i->w
                    inDegree[w]++;
                }
            }
            LinkedList<Integer> queue = new LinkedList<>();
            for (int i = 0; i < v; ++i) {
                if (inDegree[i] == 0) queue.add(i);
            }
            while (!queue.isEmpty()) {
                int i = queue.remove();
                System.out.print("->" + i);
                for (int j = 0; j < adj[i].size(); ++j) {
                    int k = adj[i].get(j);
                    inDegree[k]--;
                    if (inDegree[k] == 0) queue.add(k);
                }
            }
        }
    }

    @Test
    /**
     * 100人从1数，3的倍数就去掉
     */
    public void test5() {
        int n = 100;
        List<Integer> left = new LinkedList<>();
        List<Integer> right = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            left.add(i);
        }
        int count = 0;
        while (true) {
            while (left.size() > 0) {
                count++;
                if (count % 3 == 0) {
                    left.remove(0);
                } else {
                    right.add(left.remove(0));
                }
            }
            if (right.size() == 1) {
                System.out.println(right.get(0));
                break;
            }
            List<Integer> tmp = left;
            left = right;
            right = tmp;
        }
    }

    private static final int[][] way = new int[][]{
            {1,3,5,9},
            {2,1,3,4},
            {5,2,6,7},
            {6,8,4,3}
    };
    @Test
    /**
     * 王争动态规划：最小cost
     */
    public void minCost() {
        int[][] cost = new int[4][4];
        cost[0][0] = 1;
        for (int i = 1; i < way.length; i++) {
            cost[0][i] = cost[0][i - 1] + way[0][i];
        }
        for (int i = 1; i < way.length; i++) {
            cost[i][0] = cost[i - 1][0] + way[i][0];
        }
        for (int i = 1; i < way.length; i++) {
            for (int j = 1; j < way.length; j++) {
                cost[i][j] = Math.min(cost[i - 1][j], cost[i][j - 1]) + way[i][j];
            }
        }
        System.out.println(cost[way.length - 1][way.length - 1]);
    }

    private static final int X = 9;
    private static final int[] k = new int[]{1, 4};
    @Test
    /**
     * 竞赛：必胜策略
     */
    public void winner() {
        boolean[] win = new boolean[X + 1];
        for (int i = 1; i <= X; i++) {
            for (int j = 0; j < k.length; j++) {
                win[i] |= (k[j] <= i) && !win[i - k[j]];
            }
        }
        System.out.println(win[X]);
    }

    @Test
    /**
     * A*
     */
    public void test6() {
        LinkedList<Edge>[] adj = new LinkedList[5];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LinkedList<>();
        }
        addNode(adj, 0, 1, 10);
    }

    private void addNode(LinkedList<Edge>[] adj, int from, int to, int capacity) {
    }

    static class Edge {
        int from;
        int capacity;
        int reverse;

        public Edge(int from, int capacity, int reverse) {
            this.from = from;
            this.capacity = capacity;
            this.reverse = reverse;
        }
    }
}
