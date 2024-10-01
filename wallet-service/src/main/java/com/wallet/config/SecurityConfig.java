package com.wallet.config;

import com.wallet.filter.JwtAuthenticationFilter;
import com.wallet.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RSAKeyRecord record;
    @Autowired
    private JwtHelper jwtHelper;

//    @Order(1)
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher(new AntPathRequestMatcher("/api/**"))
//                .authorizeHttpRequests(authorize -> authorize
////            .requestMatchers("/api/wallet/create/**").permitAll()
//                        .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2ResourceServer ->
//                        oauth2ResourceServer.jwt(jwtConfigurer ->
//                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
//                                        .decoder(accessTokenDecoder()))
//                )
//                .addFilterBefore(new JwtAuthenticationFilter(accessTokenDecoder(), jwtHelper), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(ex -> {
//                    ex.authenticationEntryPoint((request, response, e) -> {
//                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication Failed");
//                    });
//                    ex.accessDeniedHandler((request, response, e) -> {
//                        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), "Access Deny");
//                    });
//                })
//                .build();
//    }

////    @Order(2)
//    @Bean
//    public SecurityFilterChain internalSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher(new AntPathRequestMatcher("/internal/wallet/create/**"))
//                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/internal/wallet/create/**")
////                        .permitAll()
//                        .anyRequest().permitAll())
//                .exceptionHandling(ex -> {
//                    ex.authenticationEntryPoint((request, response, e) -> {
//                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication Failed");
//                    });
//                    ex.accessDeniedHandler((request, response, e) -> {
//                        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), "Access Deny");
//                    });
//                })
//                .build();
//    }

    @Order(2)
    @Bean
    public SecurityFilterChain internalSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher(new AntPathRequestMatcher("/internal/wallet/create/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/internal/wallet/create/**").permitAll() // Temporarily allow access
                        .anyRequest().permitAll())
                .build();
    }

    @Bean(name = "accessTokenDecoder")
    public JwtDecoder accessTokenDecoder() { // it is use for Access Tokens
        // Configure JwtDecoder for access tokens
        return NimbusJwtDecoder.withPublicKey(record.rsaPublicKey()).build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
