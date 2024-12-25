package com.castile.common.zk.client;

import com.castile.common.zk.config.ZkConfigs;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author castile
 * @date 2024-12-23 23:18
 */
@Slf4j
public class CuratorZkClient {

    private CuratorFramework curator;

    public CuratorZkClient(ZkConfigs zkConfigs) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkConfigs.getBaseSleepTime(), zkConfigs.getMaxRetries());

        curator = CuratorFrameworkFactory.builder()
                .connectString(zkConfigs.getZkServers())
                .sessionTimeoutMs(zkConfigs.getSessionTimeout())
                .connectionTimeoutMs(zkConfigs.getConnectionTimeout())
                .retryPolicy(retryPolicy)
                .namespace(zkConfigs.getNamespace())
                .build();

        curator.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                if (newState == ConnectionState.CONNECTED) {
                    System.out.println("Connected to zookeeper");
                }else if(newState == ConnectionState.LOST){
                    System.out.println("Lost connection to zookeeper");
                }else if(newState == ConnectionState.RECONNECTED){
                    System.out.println("Reconnected to zookeeper");
                }
            }
        });
        curator.start();

        try {
            boolean connected = curator.blockUntilConnected(10000, TimeUnit.MILLISECONDS);
            if (connected){
                log.warn("Connected to zookeeper");
            }else {
                log.error("Failed to connect to zookeeper");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public CuratorFramework getCurator() {
        return curator;
    }
}
