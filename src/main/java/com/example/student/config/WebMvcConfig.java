package com.example.student.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**") // 指的是对外暴露的访问路径 如果添加了拦截器配置，一定要保证该路径是放行的才能直接访问，否则也要被拦截判断
                .addResourceLocations("classpath:/static/"); // 指的是内部文件放置的目录
    }
}
