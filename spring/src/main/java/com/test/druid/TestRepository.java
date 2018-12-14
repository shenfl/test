package com.test.druid;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;

import static org.junit.Assert.assertEquals;

/**
 * 测试SchemaRepository
 * https://github.com/alibaba/druid/wiki/SQL_Schema_Repository
 */
public class TestRepository {
    public static void main(String[] args) {
        // SchemaRepository是和数据库类型相关的，构造时需要传入dbType
        final String dbType = JdbcConstants.MYSQL;
        SchemaRepository repository = new SchemaRepository(dbType);

        repository.console("use sc00;");

        String sql = "CREATE TABLE `test1` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                "  `c_tinyint` tinyint(4) DEFAULT '1' COMMENT 'tinyint',\n" +
                "  `c_smallint` smallint(6) DEFAULT 0 COMMENT 'smallint',\n" +
                "  `c_mediumint` mediumint(9) DEFAULT NULL COMMENT 'mediumint',\n" +
                "  `c_int` int(11) DEFAULT NULL COMMENT 'int',\n" +
                "  `c_bigint` bigint(20) DEFAULT NULL COMMENT 'bigint',\n" +
                "  `c_decimal` decimal(10,3) DEFAULT NULL COMMENT 'decimal',\n" +
                "  `c_date` date DEFAULT '0000-00-00' COMMENT 'date',\n" +
                "  `c_datetime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT 'datetime',\n" +
                "  `c_timestamp` timestamp NULL DEFAULT NULL COMMENT 'timestamp'  ON UPDATE CURRENT_TIMESTAMP ,\n" +
                "  `c_time` time DEFAULT NULL COMMENT 'time',\n" +
                "  `c_char` char(10) DEFAULT NULL COMMENT 'char',\n" +
                "  `c_varchar` varchar(10) DEFAULT 'hello' COMMENT 'varchar',\n" +
                "  `c_blob` blob COMMENT 'blob',\n" +
                "  `c_text` text COMMENT 'text',\n" +
                "  `c_mediumtext` mediumtext COMMENT 'mediumtext',\n" +
                "  `c_longblob` longblob COMMENT 'longblob',\n" +
                "  PRIMARY KEY (`id`,`c_tinyint`),\n" +
                "  UNIQUE KEY `uk_a` (`c_varchar`,`c_mediumint`),\n" +
                "  KEY `k_c` (`c_tinyint`,`c_int`),\n" +
                "  KEY `k_d` (`c_char`,`c_bigint`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1769503 DEFAULT CHARSET=utf8mb4 COMMENT='10000000'";

        repository.console(sql);

        // 在如下的代码中可以知道repository中已经存在表test1
        MySqlCreateTableStatement createTableStmt = (MySqlCreateTableStatement) repository.findTable("test1").getStatement();
        assertEquals(21, createTableStmt.getTableElementList().size());

        // 通过执行命令"show columns from test1"可以获得mysql console风格的输出
        assertEquals("+--------------+---------------+------+-----+---------------------+-----------------------------+\n" +
                        "| Field        | Type          | Null | Key | Default             | Extra                       |\n" +
                        "+--------------+---------------+------+-----+---------------------+-----------------------------+\n" +
                        "| id           | bigint(20)    | NO   | PRI | NULL                | auto_increment              |\n" +
                        "| c_tinyint    | tinyint(4)    | YES  | PRI | 1                   |                             |\n" +
                        "| c_smallint   | smallint(6)   | YES  |     | 0                   |                             |\n" +
                        "| c_mediumint  | mediumint(9)  | YES  |     | NULL                |                             |\n" +
                        "| c_int        | int(11)       | YES  |     | NULL                |                             |\n" +
                        "| c_bigint     | bigint(20)    | YES  |     | NULL                |                             |\n" +
                        "| c_decimal    | decimal(10,3) | YES  |     | NULL                |                             |\n" +
                        "| c_date       | date          | YES  |     | 0000-00-00          |                             |\n" +
                        "| c_datetime   | datetime      | YES  |     | 0000-00-00 00:00:00 |                             |\n" +
                        "| c_timestamp  | timestamp     | YES  |     | NULL                | on update CURRENT_TIMESTAMP |\n" +
                        "| c_time       | time          | YES  |     | NULL                |                             |\n" +
                        "| c_char       | char(10)      | YES  | MUL | NULL                |                             |\n" +
                        "| c_varchar    | varchar(10)   | YES  | MUL | hello               |                             |\n" +
                        "| c_blob       | blob          | YES  |     | NULL                |                             |\n" +
                        "| c_text       | text          | YES  |     | NULL                |                             |\n" +
                        "| c_mediumtext | mediumtext    | YES  |     | NULL                |                             |\n" +
                        "| c_longblob   | longblob      | YES  |     | NULL                |                             |\n" +
                        "+--------------+---------------+------+-----+---------------------+-----------------------------+\n",
                repository.console("show columns from test1"));

        // 执行alter语句，修改repository中内容
        repository.console("alter table test1 drop column c_decimal;");
        assertEquals(20, createTableStmt.getTableElementList().size());

        assertEquals("+--------------+--------------+------+-----+---------------------+-----------------------------+\n" +
                        "| Field        | Type         | Null | Key | Default             | Extra                       |\n" +
                        "+--------------+--------------+------+-----+---------------------+-----------------------------+\n" +
                        "| id           | bigint(20)   | NO   | PRI | NULL                | auto_increment              |\n" +
                        "| c_tinyint    | tinyint(4)   | YES  | PRI | 1                   |                             |\n" +
                        "| c_smallint   | smallint(6)  | YES  |     | 0                   |                             |\n" +
                        "| c_mediumint  | mediumint(9) | YES  |     | NULL                |                             |\n" +
                        "| c_int        | int(11)      | YES  |     | NULL                |                             |\n" +
                        "| c_bigint     | bigint(20)   | YES  |     | NULL                |                             |\n" +
                        "| c_date       | date         | YES  |     | 0000-00-00          |                             |\n" +
                        "| c_datetime   | datetime     | YES  |     | 0000-00-00 00:00:00 |                             |\n" +
                        "| c_timestamp  | timestamp    | YES  |     | NULL                | on update CURRENT_TIMESTAMP |\n" +
                        "| c_time       | time         | YES  |     | NULL                |                             |\n" +
                        "| c_char       | char(10)     | YES  | MUL | NULL                |                             |\n" +
                        "| c_varchar    | varchar(10)  | YES  | MUL | hello               |                             |\n" +
                        "| c_blob       | blob         | YES  |     | NULL                |                             |\n" +
                        "| c_text       | text         | YES  |     | NULL                |                             |\n" +
                        "| c_mediumtext | mediumtext   | YES  |     | NULL                |                             |\n" +
                        "| c_longblob   | longblob     | YES  |     | NULL                |                             |\n" +
                        "+--------------+--------------+------+-----+---------------------+-----------------------------+\n",
                repository.console("show columns from test1"));
    }
}
