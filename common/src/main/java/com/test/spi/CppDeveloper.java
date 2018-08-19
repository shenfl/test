package com.test.spi;

/**
 * Created by shenfl on 2018/8/18
 */
public class CppDeveloper implements Developer {
    public CppDeveloper() {
        System.out.println("cpp developer");
    }
    @Override
    public String getProgramme() {
        return "cpp";
    }
}
