package com.castile.casspringbootweb.demos.zk;

import com.castile.common.zk.client.CuratorZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author castile
 * @date 2024-12-24 00:05
 */
@Component
public class ZkConfigCacheListener implements InitializingBean {
    @Autowired
    private CuratorZkClient curatorZkClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        intiCache();
    }

    private void intiCache(){
        CuratorCacheListener curatorCacheListener = CuratorCacheListener.builder().forTreeCache(this.curatorZkClient.getCurator(), new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("[TreeCacheListener]: receive event " + treeCacheEvent);
            }
        }).build();
        CuratorCache cache = CuratorCache.build(this.curatorZkClient.getCurator(), "/mateinfo",
                CuratorCache.Options.SINGLE_NODE_CACHE);
        cache.listenable().addListener(curatorCacheListener);
        cache.start();
    }
}
