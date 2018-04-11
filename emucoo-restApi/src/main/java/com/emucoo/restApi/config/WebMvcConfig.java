package com.emucoo.restApi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.emucoo.restApi.config.interceptor.ApiInterceptor;
import com.emucoo.restApi.config.interceptor.FormTokenInterceptor;

/**
 * @author fujg
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");
        //配置防止表单重复提交拦截器
        registry.addInterceptor(new FormTokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new ApiInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/index/login").excludePathPatterns("/api/index/resetPwd");
        super.addInterceptors(registry);
    }
}
