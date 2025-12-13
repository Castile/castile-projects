package com.castile.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文管理器
 *
 * @author castile
 * @date 2025-05-21 下午11:50
 */
public class ContextManager {
    // 使用ThreadLocal来存储每个线程的上下文信息
    private static final ThreadLocal<Map<String, Object>> contextHolder = ThreadLocal.withInitial(HashMap::new);

    /**
     * 租户id字段
     *
     */
    public static final String TENANTID = "x-biz-tenantId";

    /**
     * nifi集群id字段
     */
    public static final String NIFI_CLUSTER_ID = "x-nifi-clusterId";

    public static String getTenantId() {
        return (String) getContextParam(TENANTID);
    }

    public static String getNifiClusterId() {
        return (String) getContextParam(NIFI_CLUSTER_ID);
    }

    /**
     * 获取当前线程的上下文信息
     *
     * @return 当前线程的上下文信息
     */
    public static Map<String, Object> getContext() {
        return contextHolder.get();
    }

    /**
     * 设置当前线程的上下文信息
     *
     * @param context 上下文信息
     */
    public static void setContext(Map<String, Object> context) {
        contextHolder.set(context);
    }

    /**
     * 清除当前线程的上下文信息
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * 设置自定义上下文参数
     *
     * @param key   参数键
     * @param value 参数值
     */
    public static void setContextParam(String key, Object value) {
        Map<String, Object> context = contextHolder.get();
        context.put(key, value);
    }

    /**
     * 获取自定义上下文参数
     *
     * @param key 参数键
     * @return 参数值
     */
    public static Object getContextParam(String key) {
        Map<String, Object> context = contextHolder.get();
        return context.get(key);
    }

    /**
     * 移除自定义上下文参数
     *
     * @param key 参数键
     */
    public static void removeContextParam(String key) {
        Map<String, Object> context = contextHolder.get();
        context.remove(key);
    }

}
