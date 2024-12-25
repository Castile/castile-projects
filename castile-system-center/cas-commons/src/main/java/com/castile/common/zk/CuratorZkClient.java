package com.castile.common.zk;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author castile
 * @date 2024-12-23 23:18
 */
public class CuratorZkClient {

    private static Supplier<CuratorZkClient> INSTANCE = Suppliers.memoize(CuratorZkClient::new);

    public static CuratorZkClient getInstance() {
        return INSTANCE.get();
    }

    private CuratorFramework curator;

    public CuratorZkClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 5);
        curator = CuratorFrameworkFactory.builder()
                .connectString("zookeeper.nifi-docker-compose.orb.local:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("")
                .build();
        curator.start();
    }

    public CuratorFramework getCurator() {
        return curator;
    }
}
