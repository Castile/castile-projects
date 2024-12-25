package com.castile.common.zk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  zookeeper 配置
 *
 * @author castile
 * @date 2024-12-23 23:44
 */
@ConfigurationProperties(prefix = "com.castile.zk")
public class ZkConfigs {

    private String zkServers;
    private int sessionTimeout;
    private int connectionTimeout;
    private int maxRetries;
    private int baseSleepTime;
    private String namespace;

    public String getZkServers() {
        return zkServers;
    }

    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    public int getBaseSleepTime() {
        return baseSleepTime;
    }

    public void setBaseSleepTime(int baseSleepTime) {
        this.baseSleepTime = baseSleepTime;
    }
}
