package com.test.arithmetic.redblacktree;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/11/1.
 */
public class Test {
    RedBlackTree<Integer> rbt;
    public Test() {
        rbt = new RedBlackTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
    }


    public void testCorrectness() {

        int c = 1000000;

        for (int i = 0; i < c; i++) {
            rbt.insert(i);
        }

        for (int i = 0; i < c; i++) {
            rbt.delete(i);
        }

//        Assert.assertEquals(0, rbt.height());
        System.out.println(rbt.height());

        for (int i = c - 1; i >= 0; i--) {
            rbt.insert(i);
        }

        for (int i = c - 1; i >= 0; i--) {
            rbt.delete(i);
        }

//        Assert.assertEquals(0, rbt.height());
        System.out.println(rbt.height());
    }

    public void testSpeed() {

        int c = 1000000;

        Set<Integer> set = new HashSet<>(c);
        for (int i = 0; i < c; i++) {
            int e = (int) (Math.random() * c);
            set.add(e);
        }

        long prev = System.currentTimeMillis();
        for (Integer e : set) {
            rbt.insert(e);
        }

        for (Integer e : set) {
            rbt.delete(e);
        }
        System.out.println(System.currentTimeMillis() - prev);

        TreeSet<Integer> ts = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        set = new HashSet<>(c);
        for (int i = 0; i < c; i++) {
            int e = (int) (Math.random() * c);
            set.add(e);
        }

        prev = System.currentTimeMillis();
        for (Integer e : set) {
            ts.add(e);
        }

        for (Integer e : set) {
            ts.remove(e);
        }
        System.out.println(System.currentTimeMillis() - prev);
    }
}
