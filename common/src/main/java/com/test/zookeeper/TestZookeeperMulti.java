package com.test.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * https://www.jianshu.com/p/dbbc640900df
 * @author shenfl
 */
public class TestZookeeperMulti {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = null;
        String address = "0.0.0.0:2181";
        int sessionTimeout = 3000;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        zk = new ZooKeeper(address, sessionTimeout, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
                if (event.getType() == Event.EventType.None) {
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();

        List<Op> ops = new ArrayList<>();
        ops.add(Op.check("/bb", 4));
        ops.add(Op.setData("/bb", "gg".getBytes(), 4)); // 4是当前版本号，更新完后是5
        zk.multi(ops);
        zk.close();
    }
}
