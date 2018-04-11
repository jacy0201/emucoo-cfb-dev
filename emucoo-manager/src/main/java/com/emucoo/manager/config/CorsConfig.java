package com.emucoo.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 原始请求的域名
        corsConfiguration.addAllowedOrigin("*");
        // 添加请求头字段Cache-Control, Expires, Content-Type等
        corsConfiguration.addAllowedHeader("*");
        // 服务器支持的所有跨域请求的方法（'GET'、'POST'）等
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //设置过滤条件
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
