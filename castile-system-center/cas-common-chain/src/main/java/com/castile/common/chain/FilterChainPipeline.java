package com.castile.common.chain;

/**
 * @author castile
 * @date 2024-12-02 00:03
 */
public class FilterChainPipeline<T extends Filter> {
    private DefaultFilterChain last;

    public DefaultFilterChain<? extends ChainContext> getFilterChain() {
        return this.last;
    }

    public FilterChainPipeline addFilter(T filter) {
        DefaultFilterChain defaultFilterChain = new DefaultFilterChain<>(this.last, filter);
        this.last = defaultFilterChain;
        return this;
    }
}
