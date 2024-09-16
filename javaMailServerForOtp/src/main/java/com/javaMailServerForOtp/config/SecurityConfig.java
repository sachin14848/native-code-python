package com.javaMailServerForOtp.config;

import com.javaMailServerForOtp.errorHandler.CustomAccessDeniedHandler;
import com.javaMailServerForOtp.errorHandler.CustomAuthenticationEntryPoint;
import com.javaMailServerForOtp.errorHandler.CustomAuthenticationFailureHandler;
import com.javaMailServerForOtp.errorHandler.jwtErrorHandler.CustomJwtAuthenticationEntryPoint;
import com.javaMailServerForOtp.jwtAuth.JwtAccessTokenFilter;
import com.javaMailServerForOtp.jwtAuth.JwtRefreshTokenFilter;
import com.javaMailServerForOtp.repo.LogoutAccessTokenRepo;
import com.javaMailServerForOtp.repo.RefreshTokenRepo;
import com.javaMailServerForOtp.utiles.JwtTokenUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final UserInfoManagerConfig userInfoManagerConfig;
    private final RSAKeyRecord rsaKeyRecord;
    private final RSAKeyRecordAccessToken rsaKeyRecordAccessToken;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepo refreshTokenRepo;
    private final LogoutAccessTokenRepo logoutAccessTokenRepo;


    @Order(1)
    @Bean
    public SecurityFilterChain signInSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .requiresChannel(channel -> channel.anyRequest().requiresSecure())//this is to ensure only HTTPS requests are allowed
//                .headers(headers -> headers.httpStrictTransportSecurity(https -> https
//                        .includeSubDomains(true).maxAgeInSeconds(31536000)))
                .userDetailsService(userInfoManagerConfig)
                .securityMatcher(new AntPathRequestMatcher("/auth/sign-in/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/otp")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:signInSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new CustomAccessDeniedHandler());
                })
                .build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(new AntPathRequestMatcher("/auth/api/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(jwtConfigurer ->
                        jwtConfigurer
                                .decoder(accessTokenDecoder())
                ))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAccessTokenFilter(accessTokenDecoder(), jwtTokenUtils, logoutAccessTokenRepo), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:apiSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new CustomJwtAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new CustomAccessDeniedHandler());
                })
                .httpBasic(withDefaults())
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000)  // 1 year
                        )
                )
                .build();
    }

    @Order(3)
    @Bean
    public SecurityFilterChain refreshTokenSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/auth/refresh-token/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer
                                .decoder(jwtDecoder())
                ))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRefreshTokenFilter(jwtDecoder(), refreshTokenRepo, jwtTokenUtils), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:refreshTokenSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .httpBasic(withDefaults())
                .build();
    }


    @Order(4)
    @Bean
    public SecurityFilterChain logoutSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(new AntPathRequestMatcher("/auth/logout/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oath2 -> oath2.jwt(withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAccessTokenFilter(accessTokenDecoder(), jwtTokenUtils, logoutAccessTokenRepo), UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                    response.getWriter().write("{\"message\":\"Logout successful\"}");
                                })
                )
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:logoutSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "refreshTokenEncoder")
    JwtEncoder jwtEncoder() {  // it is use for Refresh Tokens
        JWK jwk = new RSAKey.Builder(rsaKeyRecord.rsaPublicKey()).privateKey(rsaKeyRecord.rsaPrivateKey()).build();
        JWKSource<SecurityContext> securityConfigJWKSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(securityConfigJWKSource);
    }

    @Bean(name = "refreshTokenDecoder")
    @Primary
    JwtDecoder jwtDecoder() { // it is use for Refresh Tokens
        return NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
    }

    @Bean(name = "accessTokenEncoder")
    JwtEncoder accessTokenEncoder() { // it is use for Access Tokens
        JWK jwk = new RSAKey.Builder(rsaKeyRecordAccessToken.rsaPublicKey()).privateKey(rsaKeyRecordAccessToken.rsaPrivateKey()).build();
        JWKSource<SecurityContext> securityConfigJWKSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(securityConfigJWKSource);
    }

    @Bean(name = "accessTokenDecoder")
    public JwtDecoder accessTokenDecoder() { // it is use for Access Tokens
        // Configure JwtDecoder for access tokens
        return NimbusJwtDecoder.withPublicKey(rsaKeyRecordAccessToken.rsaPublicKey()).build();
    }


    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
