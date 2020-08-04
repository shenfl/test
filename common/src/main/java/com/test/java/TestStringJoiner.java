package com.test.java;

import java.util.StringJoiner;

/**
 * @author shenfl
 */
public class TestStringJoiner {
    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner("\", \"", "{\"", "\"}");
        stringJoiner.add("aa");
        stringJoiner.add("bb");
        System.out.println(stringJoiner.toString());
    }
}
