package com.test.lucene;

import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.ChecksumIndexInput;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shenfl on 2018/5/30
 */
public class TestReadInfo {
    public static void main(String[] args) throws IOException {
        Directory dir = FSDirectory.open(Paths.get("/Users/dasouche1/IdeaProjects/test/common/data1"));

        ChecksumIndexInput checksumIndexInput = dir.openChecksumInput("segments_2", IOContext.READ);
        int i = checksumIndexInput.readInt();
        System.out.println(i);
        System.out.println(checksumIndexInput.readLong());
        System.out.println(checksumIndexInput.readInt());


        SegmentInfos infos = SegmentInfos.readCommit(dir, "segments_2");
        System.out.println(infos.version);
        System.out.println(infos.files(true));
        System.out.println(infos.counter);
    }
}
