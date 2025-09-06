package com.BubbleWrap.SearchEmptySeat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 핸들러 설정 - API 경로는 제외
        registry.addResourceHandler("/api/files/**")
                .addResourceLocations("file:C://Temp/ses/");
        
        // 다른 정적 리소스는 기본 설정 사용
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        // API 경로들은 정적 리소스에서 제외
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/");
    }
}
