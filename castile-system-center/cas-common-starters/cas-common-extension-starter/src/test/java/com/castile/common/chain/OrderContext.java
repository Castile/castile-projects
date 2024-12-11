package com.castile.common.chain;

import com.castile.common.chain.model.OrderRegisterModel;
import com.castile.common.chain.selector.FilterSelector;
import lombok.Getter;
import lombok.Setter;

/**
 * @author castile
 * @date 2024-12-02 01:10
 */
public class OrderContext extends AbstractChainContext{
    @Getter
    @Setter
    private OrderRegisterModel orderRegisterModel;
    public OrderContext(String bizCode, FilterSelector selector) {
        super(bizCode, selector);
    }

    @Override
    public boolean continueChain() {
        return true;
    }
}
