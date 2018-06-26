package com.test.mybatis.pojo;

/**
 * Created by shenfl on 2018/5/31
 */
public class TestDo {
    private int uid;
    private String name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestDo{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                '}';
    }
}
