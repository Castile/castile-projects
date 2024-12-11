package com.castile.samples.handler;

import com.castile.context.ContextManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author castile
 * @date 2024-12-04 00:57
 */
public class ContextHandler implements Handler {
    private static Logger LOGGER = LoggerFactory.getLogger(ContextHandler.class);
    @Override
    public void handle(Invocation invocation, AsyncResponse asyncResp) throws Exception {
        Map<String, String> context = invocation.getContext();
        LOGGER.warn("###ContextHandler#### get tenantId:" + ContextManager.getTenantId());
        if (StringUtils.isNotEmpty(ContextManager.getTenantId())){
            invocation.getContext().put("x-biz-tenantId", ContextManager.getTenantId());
        }
        LOGGER.warn("invoke context:{}", context);
        invocation.next(asyncResp);
    }
}
