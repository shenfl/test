package com.test.spring.transaction;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by shenfl on 2018/6/18
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {



    @Override
    public void outMoney(String out, double money) {
        getJdbcTemplate().update("UPDATE account SET money = money - ? where name = ?", money, out);
    }

    @Override
    public void inMoney(String in, double money) {
        getJdbcTemplate().update("UPDATE account SET money = money + ? where name = ?", money, in);
    }

    @Override
    public void outMoney1(String out, double money) {
        getJdbcTemplate().update("UPDATE account1 SET money = money - ? where name = ?", money, out);
    }

    @Override
    public void inMoney1(String in, double money) {
        getJdbcTemplate().update("UPDATE account1 SET money = money + ? where name = ?", money, in);
    }
}
