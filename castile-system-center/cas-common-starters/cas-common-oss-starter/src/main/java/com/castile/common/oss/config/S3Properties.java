package com.castile.common.oss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author castile
 * @date 2024-12-18 21:14
 */
@ConfigurationProperties(prefix = "com.castile.oss")
public class S3Properties {
    private boolean enabled = true;

    private String accessKey;
    private String secretKey;
    private String region;
    private String endpoint;
    private boolean pathStyleAccess = true;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isPathStyleAccess() {
        return pathStyleAccess;
    }

    public void setPathStyleAccess(boolean pathStyleAccess) {
        this.pathStyleAccess = pathStyleAccess;
    }
}
