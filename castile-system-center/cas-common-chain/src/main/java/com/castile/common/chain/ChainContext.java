package com.castile.common.chain;

import com.castile.common.chain.selector.FilterSelector;

/**
 *  处理链上下文接口
 *
 * @author castile
 * @date 2024-12-01 23:11
 */
public interface ChainContext {
    /**
     * 获取业务编码
     *
     * @return 业务编码
     */
    String getBizCode();


    /**
     * 获取过滤器选择器
     * @return
     */
    FilterSelector getFilterSelector();

    /**
     * 是否继续
     *
     * @return
     */
    boolean continueChain();
}
