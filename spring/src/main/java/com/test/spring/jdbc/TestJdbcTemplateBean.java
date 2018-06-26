package com.test.spring.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shenfl on 2018/6/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-jdbc.xml"})
public class TestJdbcTemplateBean {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    public void test() {
        jdbcTemplate.update("INSERT into dept VALUES (NULL, ?, ?)", "mm", "ll");
    }
    @Test
    public void test1() {
        Dept dept = jdbcTemplate.queryForObject("select * from dept where id=?", new DeptMapper(), 2374899);
        System.out.println(dept);
    }


    class DeptMapper implements RowMapper<Dept> {

        @Override
        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dept dept = new Dept();
            dept.setId(rs.getInt("id"));
            dept.setName(rs.getString("name"));
            dept.setName1(rs.getString("name1"));
            return dept;
        }
    }
    static class Dept {
        private int id;
        private String name;
        private String name1;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName1() {
            return name1;
        }

        @Override
        public String toString() {
            return "Dept{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", name1='" + name1 + '\'' +
                    '}';
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }
    }
}
