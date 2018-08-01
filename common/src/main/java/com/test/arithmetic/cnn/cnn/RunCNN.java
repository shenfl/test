package com.test.arithmetic.cnn.cnn;

import com.test.arithmetic.cnn.dataset.Dataset;
import com.test.arithmetic.cnn.util.ConcurenceRunner;
import com.test.arithmetic.cnn.util.TimedTest;
import org.junit.Test;

/**
 * https://github.com/BigPeng/JavaCNN
 * https://www.cnblogs.com/fengfenggirl/p/cnn_implement.html
 */
public class RunCNN {

	public static void runCnn() {
		//创建一个卷积神经网络
		CNN.LayerBuilder builder = new CNN.LayerBuilder();
		builder.addLayer(Layer.buildInputLayer(new Layer.Size(28, 28)));
		builder.addLayer(Layer.buildConvLayer(6, new Layer.Size(5, 5)));
		builder.addLayer(Layer.buildSampLayer(new Layer.Size(2, 2)));
		builder.addLayer(Layer.buildConvLayer(12, new Layer.Size(5, 5)));
		builder.addLayer(Layer.buildSampLayer(new Layer.Size(2, 2)));
		builder.addLayer(Layer.buildOutputLayer(10));
		CNN cnn = new CNN(builder, 50);

		//导入数据集
		String fileName = "/Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/arithmetic/cnn/data/train.format";
		Dataset dataset = Dataset.load(fileName, ",", 784);
		cnn.train(dataset, 3);//
		String modelName = "/Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/arithmetic/cnn/data/model.cnn";
		cnn.saveModel(modelName);
		dataset.clear();
		dataset = null;

		//预测
		// CNN cnn = CNN.loadModel(modelName);	
		Dataset testset = Dataset.load("/Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/arithmetic/cnn/data/test.format", ",", -1);
		cnn.predict(testset, "/Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/arithmetic/cnn/data/test.predict");
	}

	public static void main(String[] args) {

		new TimedTest(new TimedTest.TestTask() {

			@Override
			public void process() {
				runCnn();
			}
		}, 1).test();
		ConcurenceRunner.stop();

	}

	@Test
	/**
	 * 打印一条数据是几
	 */
	public void validate() {
		String s = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
		String[] split = s.split(",");
		int count = 0;
		for (String s1 : split) {
			System.out.print(s1);
			count++;
			if (count % 28 == 0) System.out.println();
		}
	}

}
