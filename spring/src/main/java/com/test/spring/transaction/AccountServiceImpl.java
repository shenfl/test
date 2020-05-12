package com.test.spring.transaction;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.io.IOException;

/**
 * Created by shenfl on 2018/6/18
 */
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private TransactionTemplate transactionTemplate;
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void does(String out, String in, double money) throws IOException {
        pay(out, in, money);
    }

    @Override
    @Transactional(rollbackFor = IOException.class) // 需要指定rollbackFor这儿的IOException才能触发回滚，当然10／0还是能回滚
    public void pay(String out, String in, double money) throws IOException {
        accountDao.outMoney(out, money);
//        int a = 10/0;
        if (true) throw new IOException("ss");
        accountDao.inMoney(in, money);


        // 手动方式
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus status) {
//                accountDao.outMoney(out, money);
//                int a = 10/0;
//                accountDao.inMoney(in, money);
//            }
//        });
    }
}
