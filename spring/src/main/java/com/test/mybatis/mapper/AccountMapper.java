package com.test.mybatis.mapper;

import com.test.mybatis.pojo.Account;

/**
 * Created by shenfl on 2018/6/20
 */
public interface AccountMapper {
    Account findById(int id);
}
