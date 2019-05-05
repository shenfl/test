package com.test.lucene;

import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.Version;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2018/5/30
 */
public class TestReadInfo {
    public static void main(String[] args) throws IOException {
//        Directory dir = FSDirectory.open(Paths.get("/Users/dasouche1/IdeaProjects/test/common/data1"));
//
//        ChecksumIndexInput checksumIndexInput = dir.openChecksumInput("segments_2", IOContext.READ);
//        int i = checksumIndexInput.readInt();
//        System.out.println(i);
//        System.out.println(checksumIndexInput.readLong());
//        System.out.println(checksumIndexInput.readInt());
//
//
//        SegmentInfos infos = SegmentInfos.readCommit(dir, "segments_2");
//        System.out.println(infos.version);
//        System.out.println(infos.files(true));
//        System.out.println(infos.counter);


        // test FST
        String inputValues[] = {"cat", "deep", "do", "dog", "dogs"};
        long outputValues[] = {5, 10, 15, 2, 8};
        PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
        Builder<Long> builder = new Builder<>(FST.INPUT_TYPE.BYTE1, outputs);
        IntsRefBuilder scratchInts = new IntsRefBuilder();
        for (int i = 0; i < inputValues.length; i++) {
            BytesRef scratchBytes = new BytesRef(inputValues[i]);
            builder.add(Util.toIntsRef(scratchBytes, scratchInts), outputValues[i]);
        }
        FST<Long> fst = builder.finish();
    }
}
