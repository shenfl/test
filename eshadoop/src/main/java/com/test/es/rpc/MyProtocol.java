package com.test.es.rpc;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface MyProtocol extends VersionedProtocol {
    public static final long versionID = 1L;

    public String println(String string);
    public int mul(int a, int b);
    public int[] sortGreat(int[] nums, int len);
}