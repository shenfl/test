package com.test.arithmetic.niuke;

/**
 * Created by shenfl on 2018/8/6
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaxOnRepeat {

    private Map<Character, Integer> map_table;
    private int[] dp;

    public static void main(String[] args) {
        MaxOnRepeat cal = new MaxOnRepeat();
        cal.calculate("aabcb");
    }

    private void calculate(String source) {
        map_table = new HashMap<>();
        dp = new int[source.length()];
        int pre = 0;
        for (int i = 0; i < source.length(); i++) {
            if (map_table.containsKey(source.charAt(i))) {
                if (i - map_table.get(source.charAt(i)) <= pre + 1) {
                    dp[i] = i - map_table.get(source.charAt(i));
                } else {
                    dp[i] = pre + 1;
                }
            } else {
                dp[i] = pre + 1;
            }
            pre = dp[i];
            map_table.put(source.charAt(i), i);
        }
        System.out.println(Arrays.toString(dp));
        int res = -1;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > res) res = dp[i];
        }
        System.out.println(res);
    }
}