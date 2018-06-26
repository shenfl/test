package com.test.spring.transaction;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by shenfl on 2018/6/18
 */
@Transactional
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
    public void pay(String out, String in, double money) {
        accountDao.outMoney(out, money);
//        int a = 10/0;
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
