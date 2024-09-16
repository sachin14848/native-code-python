package com.cricketService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate rt = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) ->{
            HttpHeaders headers = request.getHeaders();
            headers.add("x-rapidapi-key", "d992bac0dbmsh14f1501e80baf93p14b1bfjsn0b4f30550721");
            headers.add("x-rapidapi-host", "cricbuzz-cricket.p.rapidapi.com");
            return execution.execute(request, body);
        });
        rt.setInterceptors(interceptors);
        return rt;
    }

}
