package com.test.lucene;

import org.apache.lucene.util.FixedBitSet;

/**
 * https://www.amazingkoala.com.cn/
 */
public class Test {
    public static void main(String[] args) {
        // https://www.amazingkoala.com.cn/Lucene/gongjulei/2019/0404/45.html
        FixedBitSet fixedBitSet = new FixedBitSet(300);
        fixedBitSet.set(3);
        fixedBitSet.set(67);
        fixedBitSet.set(70);
        fixedBitSet.set(179);
        fixedBitSet.set(195);
        fixedBitSet.set(313);
        System.out.println(fixedBitSet.get(4));
        System.out.println(fixedBitSet.get(70));


    }
}
