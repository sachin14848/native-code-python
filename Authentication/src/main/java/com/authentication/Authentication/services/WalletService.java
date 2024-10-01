package com.authentication.Authentication.services;

import com.authentication.Authentication.dto.NewCommonResponse;
import com.authentication.Authentication.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    private final RestTemplate restTemplate;
    private static final String WALLET_SERVICE_URL = "http://localhost:8086/internal/wallet/create/";

    public void create(Long userId) {
        String walletCreateUrl = WALLET_SERVICE_URL + userId;

        // Optional: Set any headers (if required)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<NewCommonResponse<?>> entity = new HttpEntity<>(null, headers);

        ParameterizedTypeReference<NewCommonResponse<?>> responseType =
                new ParameterizedTypeReference<NewCommonResponse<?>>() {};
        // Invoke the Wallet Service to create a wallet for the user
        ResponseEntity<NewCommonResponse<?>> response = restTemplate.exchange(
                walletCreateUrl,
                HttpMethod.POST,
                entity,
                responseType
        );
        log.info("New Common Response : {}", Objects.requireNonNull(response.getBody()).getMessage());
        log.error("New Common Response : {}", Objects.requireNonNull(response.getBody()).getError());

        // Check response status or body if needed
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Wallet created successfully for user: " + userId);
        } else {
            System.out.println("Failed to create wallet for user: " + userId);
        }

    }

}
