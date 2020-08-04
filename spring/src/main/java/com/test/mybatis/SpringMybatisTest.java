package com.test.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.test.mybatis.mapper.AccountMapper;
import com.test.mybatis.pojo.Account;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 原始配置文件方式：https://www.sohu.com/a/316682057_100078571
 * 注解方式：https://www.cnblogs.com/xums/p/12642139.html
 * Created by shenfl on 2018/6/20
 */

@Configuration
// 一定要配，不管mapperLocation有没有
@MapperScan("com.test.mybatis.mapper")
public class SpringMybatisTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringMybatisTest.class);
        AccountMapper accountMapper = (AccountMapper) context.getBean("accountMapper");
        Account account = accountMapper.findById(72);
        System.out.println(account);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        try {
            // 可以不配，只要mapper文件与接口类在一个包内
            Resource[] resources = resourceResolver.getResources("classpath*:com/test/mybatis/mapper/*.xml");
            sqlSessionFactoryBean.setMapperLocations(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource(){
        DruidDataSource driverManagerDataSource = new DruidDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://172.17.40.234/canal1?useSSL=false");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("123456");
        return driverManagerDataSource;
    }
}
