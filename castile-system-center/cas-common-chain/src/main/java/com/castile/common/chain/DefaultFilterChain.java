package com.castile.common.chain;

import java.util.Objects;

/**
 * @author castile
 * @date 2024-12-01 23:18
 */
public class DefaultFilterChain<T extends ChainContext> implements FilterChain<T> {

    /**
     * 下一个处理链
     */
    private FilterChain<T> next;

    private Filter<T> filter;

    public DefaultFilterChain(FilterChain<T> chain, Filter<T> filter) {
        this.next = chain;
        this.filter = filter;
    }

    @Override
    public void handle(T context) {
        filter.doFilter(context, this);
    }

    @Override
    public void fireNext(T context) {
        FilterChain<T> nextChain = this.next;
        if (Objects.nonNull(nextChain)) {
            nextChain.handle(context);
        }
    }
}
