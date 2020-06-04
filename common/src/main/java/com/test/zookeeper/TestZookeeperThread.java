package com.test.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * callback和watch是同一个thread
 * @author shenfl
 */
public class TestZookeeperThread {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("0.0.0.0:2181", 2000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    Event.EventType type = event.getType();
                    if (type == Event.EventType.None) {
                        countDownLatch.countDown();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zooKeeper.exists("/testRoot", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDeleted) {
                    System.out.println("delete in");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("delete out");
                }
            }
        }, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("callback in");
                switch (KeeperException.Code.get(rc)) {
                    case CONNECTIONLOSS:
                        System.out.println();
                        break;
                    case OK:
                        System.out.println("callback ok");
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case NONODE:
                        System.out.println("It sounds like the previous master is gone, " +
                                "so let's run for master again.");
                        break;
                    default:
                        break;
                }
                System.out.println("callback out");
            }
        }, null);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
