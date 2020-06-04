package com.test.zookeeper;

/**
 * Created by shenfl on 2018/6/15
 */
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.ZooKeeper;
/**
 * 拥有某种权限的zookeeper客户端，该权限与AuthZookeeper类的权限不一致
 * 所以AuthZookeeper类创建的节点，该客户端不可以对该节点进行相应的操作(如：节点数据的获取：修改数据，删除数据等)
 * 但可以进行创建新的节点
 * @author adai
 * @since 20170912 11:02
 *
 */
public class AuthBadZookeeper {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ZooKeeper zoo = null ;

    public AuthBadZookeeper(){
        try {
            zoo = new ZooKeeper(ZookeeperUtil.CONNECT_ADDR,
                    ZookeeperUtil.SESSION_TIMEOUT,
                    new ZookeeperWatcher(countDownLatch,"BadAuthZookeeper"));
            //授权  参数1：权限类型    参数2:对应的正确权限（AuthZookeeper创建的节点权限不一致）
            zoo.addAuthInfo(ZookeeperUtil.AUTH_TYPE, ZookeeperUtil.BADAUTH.getBytes());
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放连接
     */
    public void close(){
        if(zoo != null ){
            try {
                zoo.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ZooKeeper getCorrectAuthZookeeper(){
        return zoo;
    }
}
