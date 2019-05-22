package com.test.arithmetic.mycnn;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class TestNd4j {
    public static void main(String[] args) {
        INDArray array = Nd4j.create(new double[][]{
                {1,1,1,0,0},
                {1,1,1,1,0},
                {1,1,1,1,1}
        });
        System.out.println(array);
    }
}
