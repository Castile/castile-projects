package com.castile.autoconfig;

import com.castile.samples.filter.ContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpFilter;

/**
 * @author castile
 * @date 2024-12-04 20:56
 */
@Configuration
public class ServiceCombAutoConfiguration {

    @Bean
    public FilterRegistrationBean<HttpFilter> contextFilterBean( ContextFilter contextFilter){
        FilterRegistrationBean<HttpFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new ContextFilter());
        registrationBean.setFilter(contextFilter);
        return registrationBean;
    }

    @Bean
    public ContextFilter contextFilter(){
        return new ContextFilter();
    }
}
