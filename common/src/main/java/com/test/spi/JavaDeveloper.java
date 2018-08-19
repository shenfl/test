package com.test.spi;

/**
 * Created by shenfl on 2018/8/18
 */
public class JavaDeveloper implements Developer {
    public JavaDeveloper() {
        System.out.println("java developer");
    }
    public String getProgramme() {
        return "java";
    }
}
