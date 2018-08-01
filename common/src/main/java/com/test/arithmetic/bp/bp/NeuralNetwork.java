package com.test.arithmetic.bp.bp;

import com.test.arithmetic.bp.data.Dataset;

public abstract class NeuralNetwork {

	public abstract void trainModel(Dataset trainSet, double threshold);
	public abstract void predict(Dataset testSet,String outName);
	

}
