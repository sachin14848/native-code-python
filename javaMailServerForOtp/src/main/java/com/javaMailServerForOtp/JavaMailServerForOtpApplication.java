package com.javaMailServerForOtp;

import com.javaMailServerForOtp.config.RSAKeyRecord;
import com.javaMailServerForOtp.config.RSAKeyRecordAccessToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties({RSAKeyRecord.class, RSAKeyRecordAccessToken.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class JavaMailServerForOtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaMailServerForOtpApplication.class, args);
    }

}
