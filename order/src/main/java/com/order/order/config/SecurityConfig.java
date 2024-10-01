package com.order.order.config;

import com.order.order.filter.JwtAuthenticationFilter;
import com.order.order.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RSAKeyRecord record;
    @Autowired
    private JwtHelper jwtHelper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
//            .requestMatchers("/api/**").hasRole("USER")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
                                        .decoder(accessTokenDecoder()))
                )
                .addFilterBefore(new JwtAuthenticationFilter(accessTokenDecoder(), jwtHelper), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, e) -> {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication Failed");
                    });
                    ex.accessDeniedHandler((request, response, e) -> {
                        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), "Access Deny");
                    });
                })
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
