package com.cricketService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/test")
//@CrossOrigin(origins = "*")
public class SSETestController {

    @CrossOrigin(origins = "*") // Replace YOUR_PORT with the port of your HTML file
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getSse() {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // Timeout after 30 minutes

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .data("timestamp: " + LocalTime.now().toString())
                        .name("time-update"));
            } catch (IOException ex) {
                emitter.completeWithError(ex);
            }
        }, 0, 1, TimeUnit.MINUTES);

        return emitter; // No need for ResponseEntity
    }

}
