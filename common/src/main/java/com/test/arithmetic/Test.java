package com.test.arithmetic;

/**
 * Created by shenfl on 2018/6/11
 */
public class Test {
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
     * 编辑距离
     */
    @org.junit.Test
    public void test2() {
        System.out.println(distance("abc", "bd"));
    }
    private int distance(String s1, String s2) {
        if (s1.length() == 0) return s2.length();
        if (s2.length() == 0) return s1.length();
        if (s1.equals(s2)) return 0;
        int temp;
        if (s1.charAt(s1.length() - 1) == s2.charAt(s2.length() - 1)) temp = 0;
        else temp = 1;
        return Math.min(Math.min(distance(s1.substring(0, s1.length() - 1), s2), distance(s2.substring(0, s2.length() - 1), s1)), distance(s1.substring(0, s1.length() - 1), s2.substring(0, s2.length() - 1)) + temp);
    }
}
