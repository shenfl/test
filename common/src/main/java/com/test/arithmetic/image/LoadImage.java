package com.test.arithmetic.image;

import org.apache.commons.io.IOUtils;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.javacpp.opencv_core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_ANYCOLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_ANYDEPTH;
import static org.bytedeco.javacpp.opencv_imgcodecs.imdecode;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

/**
 * Created by shenfl on 2017/2/6.
 * from dl4j NativeImageLoader
 */
public class LoadImage {
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("/Users/dasouche1/IdeaProjects/dl4j-examples/dl4j-examples/target/classes/DataExamples/ImagePipeline/labelC/image_0416.jpg");
        byte[] bytes = IOUtils.toByteArray(is);

        opencv_core.Mat image = imdecode(new opencv_core.Mat(bytes), CV_LOAD_IMAGE_ANYDEPTH | CV_LOAD_IMAGE_ANYCOLOR);

        opencv_core.Mat scaled;
        resize(image, scaled = new opencv_core.Mat(), new opencv_core.Size(50, 30));

        int rows = scaled.rows();
        int cols = scaled.cols();
        int channels = scaled.channels();
        Indexer idx = scaled.createIndexer();
        UByteIndexer ubyteidx = (UByteIndexer)idx;
        System.out.println("start");
        for (int k = 0; k < channels; k++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(ubyteidx.get(i, j, k) + ",");
                }
                System.out.println();
            }
            System.out.println("-------------------------------------------------");
        }
    }

}