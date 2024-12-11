package com.castile.common.chain.filter;

import com.castile.common.chain.AbstractFilter;
import com.castile.common.chain.OrderContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;

/**
 * @author castile
 * @date 2024-12-02 01:23
 */
@Slf4j
public class SaveRegisterLogFilter extends AbstractFilter<OrderContext> {
    @Override
    protected void handle(OrderContext context) {
        log.warn("Save register log");
    }
}
