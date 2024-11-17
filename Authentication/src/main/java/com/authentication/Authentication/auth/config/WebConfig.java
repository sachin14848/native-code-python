package com.authentication.Authentication.order.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow specific origins and allow credentials
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500") // List specific allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // Allow credentials
                .allowedHeaders("*"); // Allow all heade
    }
}

