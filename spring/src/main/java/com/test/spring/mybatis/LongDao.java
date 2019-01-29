package com.test.spring.mybatis;

public interface LongDao {
    @LongSelect(sql = "hello")
    void save();
}
