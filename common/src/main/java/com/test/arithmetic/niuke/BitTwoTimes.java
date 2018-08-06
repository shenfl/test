package com.test.arithmetic.niuke;

/**
 * Created by shenfl on 2018/8/6
 */
public class BitTwoTimes {
    //    private int[] arr = {2, 65, 77, 43, 4, 22, 98, 544, 2, 65, 65, 77, 43, 4, 22, 98, 544};
    private int[] arr = {2, 65, 77, 43, 4, 22, 98, 544, 2, 65, 65, 77, 43, 43, 4, 22, 98, 544};
    public static void main(String[] args) {
        BitTwoTimes twoTimes = new BitTwoTimes();
//        twoTimes.one();
        twoTimes.two();
    }

    /**
     * 两个数字出现了奇数次
     */
    private void two() {
        int res1 = 0;
        for (int i = 0; i < arr.length; i++) {
            res1 ^= arr[i];
        }
        System.out.println(res1);
        int k = 0, tmp = res1;
        while ((tmp & 1) == 0) {
            k++;
            tmp >>= 1;
        }
        System.out.println(k);
        int help = (int)Math.pow(2, k), res2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & help) != 0) {
                res2 ^= arr[i];
            }
        }
        res1 ^= res2;
        System.out.println(res1);
        System.out.println(res2);
    }
    /**
     * 只有一个数字出现了奇数次
     */
    private void one() {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res ^= arr[i];
        }
        System.out.println(res);
    }
}
