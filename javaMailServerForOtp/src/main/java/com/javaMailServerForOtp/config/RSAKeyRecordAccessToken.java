package com.javaMailServerForOtp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "access")
public record RSAKeyRecordAccessToken(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {

}