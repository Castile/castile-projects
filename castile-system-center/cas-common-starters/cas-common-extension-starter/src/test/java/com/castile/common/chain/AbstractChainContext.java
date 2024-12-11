package com.castile.common.chain;

import com.castile.common.chain.selector.FilterSelector;

/**
 * @author castile
 * @date 2024-12-02 01:12
 */
public abstract class AbstractChainContext implements ChainContext {
    private final String bizCode;
    private final FilterSelector selector;

    public AbstractChainContext(String bizCode, FilterSelector selector) {
        this.bizCode = bizCode;
        this.selector = selector;
    }

    @Override
    public String getBizCode() {
        return this.bizCode;
    }

    @Override
    public FilterSelector getFilterSelector() {
        return this.selector;
    }
}
