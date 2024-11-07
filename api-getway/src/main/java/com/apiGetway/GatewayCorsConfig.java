//package com.apiGetway;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//@Configuration
//public class GatewayCorsConfig {
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOrigin("http://127.0.0.1:5500"); // Allow specific origin
//        corsConfig.addAllowedMethod("*");  // Allow all methods (GET, POST, etc.)
//        corsConfig.addAllowedHeader("*");  // Allow all headers
//        corsConfig.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/live-scores/**", corsConfig);
//
//        return new CorsWebFilter(source);
//    }
//}
