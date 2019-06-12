package com.test.java;

import sun.misc.JavaNioAccess;
import sun.misc.SharedSecrets;
import sun.misc.VM;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;

/**
 * https://www.jianshu.com/p/744bd77726cd
 */
public class TestDirectMemory {
    public static void main(String[] args) {
        testGetMaxDirectMemory();
    }
    public static BufferPoolMXBean getDirectBufferPoolMBean(){
        return ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class)
                .stream()
                .filter(e -> e.getName().equals("direct"))
                .findFirst()
                .orElseThrow(null);
    }

    public static JavaNioAccess.BufferPool getNioBufferPool(){
        return SharedSecrets.getJavaNioAccess().getDirectBufferPool();
    }

    /**
     * -XX:MaxDirectMemorySize=60M
     */
    public static void testGetMaxDirectMemory(){
        ByteBuffer.allocateDirect(25*1024*1024);
        System.out.println(Runtime.getRuntime().maxMemory() / 1024.0 / 1024.0);
        System.out.println(VM.maxDirectMemory() / 1024.0 / 1024.0);
        System.out.println(getDirectBufferPoolMBean().getTotalCapacity() / 1024.0 / 1024.0);
        System.out.println(getNioBufferPool().getTotalCapacity() / 1024.0 / 1024.0);
    }
}
