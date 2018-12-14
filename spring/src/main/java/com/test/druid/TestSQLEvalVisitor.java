package com.test.druid;

import com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Assert;

public class TestSQLEvalVisitor {
    public static void main(String[] args) {
        // between
        Assert.assertEquals(false, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? between 1 and 3", 0));
        Assert.assertEquals(true, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? between 1 and 3", 2));

        // not between
        Assert.assertEquals(true, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? not between 1 and 3", 0));
        // case when
        Assert.assertEquals(111, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "case ? when 0 then 111 else 222 end", 0));

        // in
        Assert.assertEquals(true, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? NOT IN (1, 2, 3)", 0));
        Assert.assertEquals(false, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? IN (1, 2, 3)", 0));

        // is null
        Assert.assertEquals(false, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? is null", 0));
        Assert.assertEquals(true, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? is null", (Object) null));

        // right function
        Assert.assertEquals("rbar", SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "right('foobarbar', 4)"));

        // instr function
        Assert.assertEquals(4, SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "instr('foobarbar', 'bar')"));
    }
}
