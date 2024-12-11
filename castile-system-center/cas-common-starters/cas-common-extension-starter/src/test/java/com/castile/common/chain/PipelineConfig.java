package com.castile.common.chain;

import com.castile.common.chain.filter.CalculateValidAmountFilter;
import com.castile.common.chain.filter.EnterpriseRouterFilter;
import com.castile.common.chain.filter.SaveRegisterLogFilter;
import com.castile.common.chain.model.OrderRegisterModel;
import com.castile.common.chain.selector.FilterSelector;
import com.castile.common.chain.selector.LocalFilterSelector;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

/**
 * @author castile
 * @date 2024-12-02 01:09
 */

public class PipelineConfig {
    @Test
    void test() {

        FilterChainPipeline<Filter<OrderContext>> filterFilterChainPipeline = new FilterChainPipeline<>();
        FilterChainPipeline filterChainPipeline = filterFilterChainPipeline
                .addFilter(new CalculateValidAmountFilter())
                .addFilter(new EnterpriseRouterFilter())
                .addFilter(new SaveRegisterLogFilter());
        //选择要执行的filter
        FilterSelector selector = getOrderRegisterSelector();

        OrderContext orderContext = new OrderContext("order-1002", selector);
        OrderRegisterModel orderRegisterModel = new OrderRegisterModel("1001", "order-1001", "198999999999999");
        orderContext.setOrderRegisterModel(orderRegisterModel);
        filterChainPipeline.getFilterChain().handle(orderContext);
    }

    /**
     * 配置订单注册的filter
     *
     * @return
     */
    private FilterSelector getOrderRegisterSelector() {
        LocalFilterSelector selector = new LocalFilterSelector();
        selector.addFilter(CalculateValidAmountFilter.class.getSimpleName());
        selector.addFilter(SaveRegisterLogFilter.class.getSimpleName());
        selector.addFilter(EnterpriseRouterFilter.class.getSimpleName());
        return selector;
    }
}
