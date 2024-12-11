package com.castile.samples.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.Constants;
import org.apache.servicecomb.context.ContextManager;
import org.apache.servicecomb.core.Const;
import org.apache.servicecomb.foundation.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author castile
 * @date 2024-12-04 20:52
 */
public class ContextFilter extends HttpFilter {
    @Value("${servicecomb.server.context.pass:false}")
    private String contextPass;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        httpServletRequest.setAttribute("x-biz-tenantId", "9000");
        String strCseContext = httpServletRequest.getHeader(Const.CSE_CONTEXT);
        System.out.println(strCseContext);
        if (StringUtils.isNotEmpty(strCseContext)) {
            Map<String, String> cseContext =
                    JsonUtils.readValue(strCseContext.getBytes(StandardCharsets.UTF_8), Map.class);

            cseContext.entrySet().forEach(entry -> {
                httpServletRequest.setAttribute(entry.getKey(), entry.getValue());
            });

            if ("true".equals(contextPass)) {
                ContextManager.setTenantId(cseContext.get(Constants.TENANTID));
            }
            chain.doFilter(request, response);
        }
    }
}
