package com.castile.common.chain;

/**
 * @author castile
 * @date 2024-12-01 23:14
 */
public interface FilterChain<T extends ChainContext> {
    /**
     * 处理当前流程
     *
     * @param context 上下文
     */
    void handle(T context);

    /**
     * 继续处理下一个Filter
     *
     * @param context 上下文
     */
    void fireNext(T context);
}
