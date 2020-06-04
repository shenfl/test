package com.test.zookeeper;

/**
 * Created by shenfl on 2018/6/15
 */
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.ZooKeeper;

/**
 *
 * @author adai
 * @since 20170912 11:00
 * 没有任何权限的zookeeper客户端，不能操作AuthZookeeper客户端创建的节点(如：节点数据的获取：修改数据，删除数据等)
 */
public class AuthNotZookeeper {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ZooKeeper zoo = null ;

    public AuthNotZookeeper(){
        try {
            zoo = new ZooKeeper(ZookeeperUtil.CONNECT_ADDR,
                    ZookeeperUtil.SESSION_TIMEOUT,
                    new ZookeeperWatcher(countDownLatch,"NotAuthZookeeper"));
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
