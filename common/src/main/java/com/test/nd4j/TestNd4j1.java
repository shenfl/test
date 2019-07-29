package com.test.nd4j;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

public class TestNd4j1 {
    public static void main(String[] args) {
        int nRows = 3;
        int nColumns = 5;
        INDArray myArray = Nd4j.zeros(nRows, nColumns);

        System.out.println("Basic INDArray information:");
        System.out.println("Num. Rows:          " + myArray.rows());
        System.out.println("Num. Columns:       " + myArray.columns());
        System.out.println("Num. Dimensions:    " + myArray.rank());
        System.out.println("Shape:              " + Arrays.toString(myArray.shape()));
        System.out.println("Length:             " + myArray.length());

        //We can print the array itself using toString method:
        System.out.println("\nArray Contents:\n" + myArray);

        //There are some other ways we can get the same or similar info
        System.out.println();
        System.out.println("size(0) == nRows:   " + myArray.size(0));                   //Also equivalent to: .shape()[0]
        System.out.println("size(1) == nCols:   " + myArray.size(1));                   //Also equivalent to: .shape()[1]
        System.out.println("Is a vector:        " + myArray.isVector());
        System.out.println("Is a scalar:        " + myArray.isScalar());
        System.out.println("Is a matrix:        " + myArray.isMatrix());
        System.out.println("Is a square matrix: " + myArray.isSquare());;



        //Let's make some modifications to our array...
        // Note that indexing starts at 0. Thus 0..2 are valid indices for rows, and 0..4 are valid indices for columns here
        myArray.putScalar(0, 1, 2.0);           //Set value at row 0, column 1 to value 2.0
        myArray.putScalar(2, 3, 5.0);           //Set value at row 2, column 3 to value 5.0
        System.out.println("\nArray after putScalar operations:");
        System.out.println(myArray);


        //We can also get individual values:
        double val0 = myArray.getDouble(0, 1);  //Get the value at row 0, column 1 - expect value 2.0 as we set this earlier
        System.out.println("\nValue at (0,1):     " + val0);

        //Finally, there are many things we can do to the array... for example adding scalars:
        INDArray myArray2 = myArray.add(1.0);   //Add 1.0 to each entry
        System.out.println("\nNew INDArray, after adding 1.0 to each entry:");
        System.out.println(myArray2);

        INDArray myArray3 = myArray2.mul(2.0);  //Multiply each entry by 2.0
        System.out.println("\nNew INDArray, after multiplying each entry by 2.0:");
        System.out.println(myArray3);
    }
}
