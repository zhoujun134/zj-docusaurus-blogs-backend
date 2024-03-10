package com.zj.zs.config;

import com.zj.zs.config.filter.LoginFilter;
import com.zj.zs.dao.UserManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ZsWebConfiguration
 * @Author zj
 * @Description
 * @Date 2024/3/10 15:08
 * @Version v1.0
 **/

@Configuration
public class ZsWebConfiguration {

    @Value("#{'${zs.boot.sso.paths}'.split(',')}")
    private List<String> filterPaths;
    @Resource
    private UserManager userManager;


    @Bean
    public FilterRegistrationBean<LoginFilter> filterRegistrationBean() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LoginFilter(userManager));	// 这里可以使用 new，也可以在 Filter 上加 @Component 注入进来
        bean.setUrlPatterns(filterPaths);
        bean.setName("loginFilter");
        bean.setOrder(1);	// 值越小，优先级越高
        return bean;
    }
}
