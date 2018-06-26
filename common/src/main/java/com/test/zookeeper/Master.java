package com.test.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Created by shenfl on 2018/6/15
 * https://my.oschina.net/wayhk/blog/674203
 */
public class Master implements Watcher {

    enum MasterState {RUNNING, ELECTED, NOT_ELECTED}
    private volatile MasterState state = MasterState.RUNNING;

    MasterState getState() {
        return state;
    }

    public static final int SESSION_TIMEOUT = 5000;
    private static final String CONNECTION_STRING = "0.0.0.0:2181";
    public static final String ZNODE_NAME = "/test_master";
    private Random random = new Random(System.currentTimeMillis());
    private ZooKeeper zookeeper;
    private String serverId = Integer.toHexString(random.nextInt());

    private volatile boolean connected = false;
    private volatile boolean expired = false;

    private void startZK() throws IOException {
        zookeeper = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, this);
    }

    private void stopZK() {
        if (zookeeper != null) {
            try {
                zookeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void enroll() {
        zookeeper.create(ZNODE_NAME, serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);
    }

    private AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    state = MasterState.ELECTED;
                    return;
                case NODEEXISTS:
                    state = MasterState.NOT_ELECTED;
                    addMasterWatcher();
                    return;
                default:
                    state = MasterState.NOT_ELECTED;
                    System.err.println("Something went wrong when running for master." +
                            KeeperException.create(KeeperException.Code.get(rc), path));

            }
        }
    };

    AsyncCallback.DataCallback masterCheckCallBack = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case NONODE:
                    // 节点未创建，再次注册
                    enroll();
                    return;
                case OK:
                    if (serverId.equals(new String(data))) {
                        state = MasterState.ELECTED;
                    } else {
                        state = MasterState.NOT_ELECTED;
                        addMasterWatcher();
                    }
                    break;
                default:
                    System.err.println("Error when reading data." + KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    private void addMasterWatcher() {
        zookeeper.exists(ZNODE_NAME, masterExistsWatcher, masterExistsCallback, null);
    }

    Watcher masterExistsWatcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if(event.getType() == Event.EventType.NodeDeleted){
                assert ZNODE_NAME.equals(event.getPath());
                enroll();
            }
        }
    };

    AsyncCallback.StatCallback masterExistsCallback = new AsyncCallback.StatCallback() {
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    addMasterWatcher();
                    break;
                case OK:
                    break;
                case NONODE:
                    state = MasterState.RUNNING;
                    enroll();
                    System.out.println("It sounds like the previous master is gone, " +
                            "so let's run for master again.");
                    break;
                default:
                    checkMaster();
                    break;
            }
        }
    };

    private void checkMaster() {
        zookeeper.getData(ZNODE_NAME, false,    masterCheckCallBack, null);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Master m = new Master( );
        m.startZK();

        while (!m.isConnected()) {
            Thread.sleep(100);
        }
        m.enroll();
        while (!m.isExpired()) {
            Thread.sleep(1000);
        }

        m.stopZK();
    }
    boolean isConnected() {
        return connected;
    }

    boolean isExpired() {
        return expired;
    }

    @Override
    public void process(WatchedEvent e) {
        System.out.println("Processing event: " + e.toString());
        if (e.getType() == Event.EventType.None) {
            switch (e.getState()) {
                case SyncConnected:
                    connected = true;
                    break;
                case Disconnected:
                    connected = false;
                    break;
                case Expired:
                    expired = true;
                    connected = false;
                    System.err.println("Session expiration");
                default:
                    break;
            }
        }
    }
}
