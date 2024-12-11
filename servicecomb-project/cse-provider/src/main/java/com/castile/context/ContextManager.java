package com.castile.context;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author castile
 * @date 2024-12-05 22:21
 */
public class ContextManager {
    private static Logger LOGGER = LoggerFactory.getLogger(ContextManager.class);

    private static InheritableThreadLocal<String> context = new InheritableThreadLocal<>();

    private String tenantId;

    public static void setTenantId(String tenantId) {
        LOGGER.warn("[###ContextManager###] setTenantId={}", tenantId);
        if (StringUtils.isEmpty(tenantId)){
            clear();
            return;
        }
        context.set(tenantId);
    }
    public static String getTenantId() {
        LOGGER.warn("[###ContextManager###] getTenantId={}", context.get());
        return context.get();
    }
    public static void clear() {
        LOGGER.warn("[###ContextManager###] clear={}", getTenantId());

        context.set(null);
    }
}
