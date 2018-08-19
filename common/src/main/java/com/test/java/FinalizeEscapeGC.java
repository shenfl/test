package com.test.java;

/**
 * Created by shenfl on 2018/8/19
 * finalize测试，深入理解java虚拟机p67
 */
public class FinalizeEscapeGC {
    private static FinalizeEscapeGC INSTANCE;

    public void isAlive() {
        System.out.println("I am alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Finalize executed");
        INSTANCE = this;
    }

    public static void main(String[] args) throws InterruptedException {
        INSTANCE = new FinalizeEscapeGC();
        INSTANCE = null;
        System.gc();
        Thread.sleep(500); // 有这句就会alive否则died
        if (INSTANCE != null) {
            INSTANCE.isAlive();
        } else {
            System.out.println("Died");
        }
    }
}
