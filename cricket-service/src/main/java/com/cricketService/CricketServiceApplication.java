package com.cricketService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CricketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CricketServiceApplication.class, args);
	}

}
