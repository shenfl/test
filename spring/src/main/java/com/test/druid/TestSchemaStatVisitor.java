package com.test.druid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.Set;

/**
 * 测试SchemaStatVisitor
 * https://github.com/alibaba/druid/wiki/SchemaStatVisitor
 * https://github.com/alibaba/druid/wiki/SchemaStatVisitor_RelationShip
 */
public class TestSchemaStatVisitor {
    public static void main(String[] args) {
        String sql = "select name, age from t_user where id = 1";

        String dbType = JdbcConstants.MYSQL;
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        SQLStatement stmt = stmtList.get(0);

        SchemaStatVisitor statVisitor = SQLUtils.createSchemaStatVisitor(dbType);
        stmt.accept(statVisitor);

        System.out.println(statVisitor.getColumns()); // [t_user.name, t_user.age, t_user.id]
        System.out.println(statVisitor.getTables()); // {t_user=Select}
        System.out.println(statVisitor.getConditions()); // [t_user.id = 1]



        sql = "create table t_org (fid int, name varchar(256))";

        stmtList = SQLUtils.parseStatements(sql, dbType);
        stmt = stmtList.get(0);

        statVisitor = SQLUtils.createSchemaStatVisitor(dbType);
        stmt.accept(statVisitor);

        System.out.println(statVisitor.getTables()); //{t_org=Create}
        System.out.println(statVisitor.getColumns()); // [t_org.fid, t_org.name]


        //
        sql = "select a.id, b.name from table1 a inner join table2 b on a.id = b.id";

        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
        stmt = parser.parseStatementList().get(0);

        statVisitor = SQLUtils.createSchemaStatVisitor(dbType);
        stmt.accept(statVisitor);

        Set<TableStat.Relationship> relationships = statVisitor.getRelationships();
        for (TableStat.Relationship relationship : relationships) {
            System.out.println(relationship); // table1.id = table2.id
        }
    }
}
