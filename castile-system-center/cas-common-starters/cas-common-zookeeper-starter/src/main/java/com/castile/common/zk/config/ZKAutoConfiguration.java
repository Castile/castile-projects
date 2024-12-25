package com.castile.common.zk.config;

import com.castile.common.zk.client.CuratorZkClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author castile
 * @date 2024-12-23 23:45
 */
@Configuration
@EnableConfigurationProperties(ZkConfigs.class)
public class ZKAutoConfiguration {

    @Bean
    public CuratorZkClient curatorZkClient(ZkConfigs zkConfigs) {
        return new CuratorZkClient(zkConfigs);
    }

}
