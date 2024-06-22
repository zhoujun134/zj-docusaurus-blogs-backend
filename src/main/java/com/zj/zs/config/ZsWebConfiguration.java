package com.zj.zs.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zj.zs.config.filter.request.BackendHttpServletRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @ClassName ZsWebConfiguration
 * @Author zj
 * @Description
 * @Date 2024/3/10 15:08
 * @Version v1.0
 **/

@Configuration
public class ZsWebConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public FilterRegistrationBean<BackendHttpServletRequestFilter> filterRegistrationBean() {
        FilterRegistrationBean<BackendHttpServletRequestFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new BackendHttpServletRequestFilter());  // 这里可以使用 new，也可以在 Filter 上加 @Component 注入进来
        bean.setUrlPatterns(Collections.singleton("/*"));
        bean.setName("BackendHttpServletRequestFilter");
        bean.setOrder(-1);  // 值越小，优先级越高
        return bean;
    }

    @Bean
    public BackendHttpServletRequestFilter backendHttpServletRequestFilter() {
        return new BackendHttpServletRequestFilter();
    }
}
