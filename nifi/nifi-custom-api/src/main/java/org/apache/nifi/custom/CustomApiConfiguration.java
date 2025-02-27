package org.apache.nifi.custom;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.core.Context;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * @author castile
 * @date 2025-02-21 下午9:31
 */

public class CustomApiConfiguration extends ResourceConfig {

    public CustomApiConfiguration(@Context ServletContext servletContext) {


        // get the application context to register the rest endpoints
        final ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        register(ctx.getBean("helloController"));

    }
}
