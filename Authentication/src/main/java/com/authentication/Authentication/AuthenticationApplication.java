package com.authentication.Authentication;

import com.authentication.Authentication.config.RSAKeyRecord;
import com.authentication.Authentication.config.RSAKeyRecordAccessToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties({RSAKeyRecord.class, RSAKeyRecordAccessToken.class})
@SpringBootApplication
@EnableAsync
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
