package com.test.mybatis;

import com.test.mybatis.pojo.TestDo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shenfl on 2018/5/31
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String resource = "SqlMapConfig.xml";
        // 得到配置文件流
        InputStream inputStream =  Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = factory.openSession();
        Object o = sqlSession.selectOne("test.find", 12); // 其中test是<mapper namespace的namespace
        System.out.println(o);

//        TestDo testDo = new TestDo();
//        testDo.setName("tt");
//        int insert = sqlSession.insert("test.insert", testDo); // 影响的行数
//        System.out.println(insert);
//        System.out.println(testDo.getUid()); // 插入的id

        sqlSession.commit();
        sqlSession.close();
    }
}
