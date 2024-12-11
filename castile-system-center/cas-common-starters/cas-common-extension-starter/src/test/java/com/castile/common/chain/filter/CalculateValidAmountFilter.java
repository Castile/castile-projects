package com.castile.common.chain.filter;

import com.castile.common.chain.AbstractFilter;
import com.castile.common.chain.OrderContext;
import com.castile.common.chain.model.OrderRegisterModel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author castile
 * @date 2024-12-02 01:17
 */
@Slf4j
public class CalculateValidAmountFilter extends AbstractFilter<OrderContext> {
    @Override
    protected void handle(OrderContext context) {
        log.warn("[CalculateValidAmountFilter] process");
        OrderRegisterModel model = context.getOrderRegisterModel();
        System.out.println(model.getBizSystemCode());
    }
}
