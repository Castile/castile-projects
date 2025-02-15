package com.castile.autoconfig;

import com.castile.samples.filter.ContextFilter;
import jakarta.servlet.http.HttpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * @author castile
 * @date 2024-12-04 20:56
 */
@Configuration
public class ServiceCombAutoConfiguration {

    @Bean
    public FilterRegistrationBean<HttpFilter> contextFilterBean(ContextFilter contextFilter){
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
