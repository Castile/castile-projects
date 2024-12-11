package com.castile.common.chain;

/**
 * @author castile
 * @date 2024-12-01 23:29
 */
public abstract class AbstractFilter<T extends ChainContext> implements Filter<T>{

    @Override
    public void doFilter(T context, FilterChain<T> chain) {
        if (context.getFilterSelector().matchFilter(this.getClass().getSimpleName())) {
            handle(context);
        }
        if (context.continueChain()){
            chain.fireNext(context);
        }
    }

    protected abstract void handle(T context);
}
