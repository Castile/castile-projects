package com.castile.samples.filter;

import com.castile.Constants;
import com.castile.context.ContextManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.foundation.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.apache.servicecomb.core.CoreConst.CSE_CONTEXT;

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
        String strCseContext = httpServletRequest.getHeader(CSE_CONTEXT);
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
