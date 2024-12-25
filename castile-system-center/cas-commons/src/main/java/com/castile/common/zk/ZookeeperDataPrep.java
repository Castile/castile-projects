package com.castile.common.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author castile
 * @date 2024-12-23 23:31
 */
public class ZookeeperDataPrep {
    private static final String CONNECT_STRING = "zookeeper.nifi-docker-compose.orb.local:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String PARENT_PATH = "/mateinfo";

    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    latch.countDown();
                }
            });
            latch.await();

            // 创建父目录
            Stat stat = zooKeeper.exists(PARENT_PATH, false);
            if (stat == null) {
                zooKeeper.create(PARENT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            // 创建多层目录和数据节点
            for (int i = 0; i < 100; i++) {
                String subPath = PARENT_PATH + "/subdir_" + (i / 10) + "/data_" + i;
                String[] paths = subPath.split("/");
                StringBuilder currentPath = new StringBuilder();
                for (int j = 1; j < paths.length; j++) {
                    currentPath.append("/").append(paths[j]);
                    stat = zooKeeper.exists(currentPath.toString(), false);
                    if (stat == null) {
                        if (j == paths.length - 1) {
                            zooKeeper.create(currentPath.toString(), ("data for " + i).getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        } else {
                            zooKeeper.create(currentPath.toString(), new byte[0],
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        } finally {
            if (zooKeeper!= null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
