package com.test.mybatis;

import com.test.mybatis.mapper.AccountMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * Created by shenfl on 2018/6/1
 */
public class MapperTest {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        TestMapper mapper = sqlSessionFactory.openSession().getMapper(TestMapper.class);
        System.out.println(mapper.find(12));
        AccountMapper accountMapper = sqlSessionFactory.openSession().getMapper(AccountMapper.class);
        System.out.println(accountMapper.findById(67));
    }
}
