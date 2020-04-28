package com.test.arithmetic;

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
}
