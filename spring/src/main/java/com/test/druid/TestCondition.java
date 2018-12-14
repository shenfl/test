package com.test.druid;

import com.alibaba.druid.sql.SQLUtils;
import org.junit.Assert;

public class TestCondition {
    public static void main(String[] args) {
        Assert.assertEquals("SELECT *" //
                + "\nFROM t" //
                + "\nWHERE id = 0", SQLUtils.addCondition("select * from t", "id = 0", null));

        Assert.assertEquals("SELECT *" //
                + "\nFROM t" //
                + "\nWHERE id = 0" //
                + "\n\tAND name = 'aaa'", SQLUtils.addCondition("select * from t where id = 0", "name = 'aaa'", null));
    }
}
