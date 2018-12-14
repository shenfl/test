package com.test.druid;

import com.alibaba.druid.sql.visitor.ParameterizedOutputVisitorUtils;
import com.alibaba.druid.util.JdbcConstants;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 测试参数化处理
 * https://github.com/alibaba/druid/wiki/SQL_Parser_Parameterize
 */
public class TestParserParameterize {
    public static void main(String[] args) {
        String dbType = JdbcConstants.MYSQL;
        String sql = "select * from t where id = 1 or id = 2 or id = 3";
        String psql = ParameterizedOutputVisitorUtils.parameterize(sql, dbType);
        assertEquals("SELECT *\n" +
                "FROM t\n" +
                "WHERE id = ?", psql);


        // 参数化SQL是输出的参数保存在这个List中
        List<Object> outParameters = new ArrayList<Object>();

        sql = "select * from t where id = 101 and age = 102 or name = 'wenshao'";
        psql = ParameterizedOutputVisitorUtils.parameterize(sql, dbType, outParameters);
        assertEquals("SELECT *\n" +
                "FROM t\n" +
                "WHERE id = ?\n" +
                "\tAND age = ?\n" +
                "\tOR name = ?", psql);

        assertEquals(3, outParameters.size());
        assertEquals(101, outParameters.get(0));
        assertEquals(102, outParameters.get(1));
        assertEquals("wenshao", outParameters.get(2));
    }
}
