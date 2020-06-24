package com.test.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 创建节点有多个用户，用户的权限不一样
 * @author shenfl
 */
public class TestAuth1 {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InterruptedException, KeeperException {
        List<ACL> acls = new ArrayList<>(2);
        Id id1 = new Id("digest", DigestAuthenticationProvider.generateDigest("shenfl:123456"));
        ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);
        Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest("zhuky:qwert"));
        ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);
        acls.add(acl1);
        acls.add(acl2);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("0.0.0.0:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(Event.KeeperState.SyncConnected == event.getState() && Event.EventType.None.equals(event.getType())){
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        // 注释掉执行会报错
//        zooKeeper.addAuthInfo("digest", "shenfl:123456".getBytes());
        zooKeeper.create("/cc", new byte[0], acls, CreateMode.PERSISTENT);
        zooKeeper.create("/cc/cc", new byte[0], acls, CreateMode.PERSISTENT);
        zooKeeper.delete("/cc/cc", -1);
        zooKeeper.delete("/cc", -1);
        zooKeeper.close();
    }
}
