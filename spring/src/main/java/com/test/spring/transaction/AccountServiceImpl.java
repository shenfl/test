package com.test.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
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
    @Autowired
    private AccountService accountService;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private TransactionTemplate transactionTemplate;
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void does(String out, String in, double money) throws IOException {
        // 这样没有事务
        pay(out, in, money);
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public void nestedPay(String out, String in, double money) throws IOException {
//        accountDao.outMoney(out, money);
//        if (true) throw new IOException("ss");
//        accountDao.inMoney(in, money);

        accountDao.outMoney1(out, money);
        if (true) throw new IOException("ss");
        accountDao.inMoney1(in, money);
    }

    @Override
    @Transactional(rollbackFor = IOException.class) // 1. 需要指定rollbackFor这儿的IOException才能触发if (true) throw new IOException("ss")回滚，当然10／0还是能回滚
    // 2. 如果配置了propagation = Propagation.SUPPORTS，Propagation.NOT_SUPPORTED,Propagation.NEVER则什么异常都不会回滚
    public void pay(String out, String in, double money) throws IOException {
        accountDao.outMoney(out, money);
        try {
            // 3. 相当于只有pay方法一个事务，这样nestedPay方法执行了哪些代码是自己决定，但是是否提交是pay方法决定
//            nestedPay(out, in, money * 5);
            // 4. 使用的事务代理对象，可以测试propagation特性
            //    默认传播属性下，如果pay不报错，nestedPay报错，全部回滚，当然pay报错nestedPay不报错也是全部回滚
            //    nestedPay使用propagation = Propagation.REQUIRES_NEW，操作account表会死锁，操作account1时，相当于是一个独立的事务，根pay方法完全没影响
            //    nestedPay使用propagation = Propagation.NESTED，则nestedPay回滚，pay可以不回滚，但是当nestedPay没报错，pay回滚时，也会让nestedPay回滚
            //    nestedPay使用propagation = Propagation.SUPPORTS（如果当前已经存在事务，那么加入该事务，否则无事务执行），则根默认一样，只有当pay的transaction注解去掉，则nestedPay相当于没有事务
            //    nestedPay使用propagation = Propagation.NOT_SUPPORTED(如果当前存在事务，挂起当前事务)，则nestedPay报错不会回滚，pay中无报错时不会回滚
            accountService.nestedPay(out, in, money * 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        int a = 10/0;
//        if (true) throw new IOException("ss");
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
