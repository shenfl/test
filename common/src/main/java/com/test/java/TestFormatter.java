package com.test.java;

import java.text.DecimalFormat;

/**
 * @author shenfl
 */
public class TestFormatter {
    public static void main(String[] args) {
        System.out.println(Math.ceil(30.08));
        System.out.println(Math.floor(1.58));
        System.out.println(Math.ceil(30.08));
        DecimalFormat format = new DecimalFormat("0.00");
        System.out.println(format.format(23.8678));
        System.out.println(format.format(8945));
        System.out.println(format.format(0.00034));
    }
}
