package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnProperty(prefix = "cn.app.filters.cors-filter", name = "enabled")
public class CORSConfiguration {

    @Value("${cn.app.filters.cors-filter.allowed-origins}")
    String corsFilterAllowedOrigins;

    @Value("${cn.app.filters.cors-filter.allowed-methods}")
    String corsFilterAllowedMethods;

    @Value("${cn.app.filters.cors-filter.allowed-headers}")
    String corsFilterAllowedHeaders;

    @Value("${cn.app.filters.cors-filter.allowed-path-pattern}")
    String corsFilterAllowedPathPattern;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(delimitedStringToList(corsFilterAllowedOrigins));
        corsConfig.setAllowedHeaders(delimitedStringToList(corsFilterAllowedHeaders));
        corsConfig.setAllowedMethods(delimitedStringToList(corsFilterAllowedMethods));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsFilterAllowedPathPattern, corsConfig);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    private List<String> delimitedStringToList(String allowedList) {
        return Arrays.stream(allowedList.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
