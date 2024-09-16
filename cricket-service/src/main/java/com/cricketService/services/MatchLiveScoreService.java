package com.cricketService.services;

import com.cricketService.dto.RapidApiLiveScore;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

//@RequiredArgsConstructor
@Service
public class MatchLiveScoreService {

    private static final Logger log = LoggerFactory.getLogger(MatchLiveScoreService.class);
    //    @Autowired
    private final RedisTemplate<String, RapidApiLiveScore> redisTemplate;

//    @Autowired
    private final RestTemplate restTemplate;

    @Value("${rapid.baseURL}")
    private String baseUrl;

    @Value("${rapid.urls.leanback}")
    private String liveScoreUrl;

    @Autowired
    public MatchLiveScoreService(@Qualifier("matchLiveScore") RedisTemplate<String, RapidApiLiveScore> liveScoreTemplate, RestTemplate rest) {
        this.redisTemplate = liveScoreTemplate;
        this.restTemplate = rest;
    }

    public RapidApiLiveScore getMatchLiveScore(int matchId) {

        // Fetch live score from Redis or API and return it
        RapidApiLiveScore scores = redisTemplate.opsForValue().get("leanback:" + matchId);
        if (scores != null) {
            return scores;
        }
        final String url = baseUrl + liveScoreUrl + matchId + "/leanback";
        RapidApiLiveScore  score = restTemplate.getForObject(url, RapidApiLiveScore.class);
        log.info("Score: {}", score);
        assert score != null;
        redisTemplate.opsForValue().set("leanback:" + matchId, score, 30, TimeUnit.SECONDS);
        return score;

    }

}
