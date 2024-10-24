package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.RapidApiLiveScore;
import com.cricketService.dto.scoreBoard.RapidScoreCardDto;
import com.cricketService.services.MatchLiveScoreService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/cricket/live-score")
public class MatchLiveScoreController {

    private final MatchLiveScoreService matchLiveScoreService;

    @RequestMapping("/{matchId}")
    public ResponseEntity<CommonResponse<RapidApiLiveScore>> getLiveScore(@PathVariable int matchId) {
        CommonResponse<RapidApiLiveScore> response = new CommonResponse<>();
        response.setData(matchLiveScoreService.getMatchLiveScore(matchId));
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setStatus(true);
        response.setMessage("Live score fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/live-cricket-score/{matchId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<RapidScoreCardDto>> streamLiveScores(@PathVariable Long matchId) {
        return Flux.interval(Duration.ofSeconds(10))
                .map(sequence -> ServerSentEvent.<RapidScoreCardDto>builder()
                        .id(String.valueOf(sequence))
                        .event("score-update")
                        .data(matchLiveScoreService.getScoreScoreCard(matchId))
                        .build())
                .onErrorResume(throwable -> {
                    // Log error and return fallback values
                    System.err.println("Error occurred while streaming: " + throwable.getMessage());
                    return Flux.empty();
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("Retries exhausted")));
    }

    @GetMapping(value = "/live-cricket-score/new/{matchId}", produces = "text/event-stream")
    public SseEmitter streamLiveScore(@PathVariable Long matchId) {
        SseEmitter emitter = new SseEmitter();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    RapidScoreCardDto score = matchLiveScoreService.getScoreScoreCard(matchId);
                    emitter.send(score);
                    Thread.sleep(20000); // simulate periodic score updates
                }
                emitter.complete();
            } catch (IOException | InterruptedException ex) {
                emitter.completeWithError(ex);
            }
        });

        emitter.onCompletion(executor::shutdown);

        emitter.onTimeout(() -> {
            emitter.complete();
            executor.shutdown();
        });

        emitter.onError((ex) -> {
            System.err.println("Error occurred: " + ex.getMessage());
            executor.shutdown();
        });

        return emitter;
    }


}
