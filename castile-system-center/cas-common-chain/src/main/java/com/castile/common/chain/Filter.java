package com.castile.common.chain;

/**
 * @author castile
 * @date 2024-12-01 23:19
 */
public interface Filter<T extends ChainContext> {

    /**
     * 过滤逻辑封装点
     *
     * @param context 上下文
     * @param chain   处理链
     */
    void doFilter(T context, FilterChain<T> chain);
}
