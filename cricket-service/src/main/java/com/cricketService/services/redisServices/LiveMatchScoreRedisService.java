package com.cricketService.services.redisServices;

import com.cricketService.dto.scoreBoard.RapidScoreCardDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LiveMatchScoreRedisService {

    private final RedisTemplate<String, RapidScoreCardDto> redisTemplate;

    public LiveMatchScoreRedisService(@Qualifier("liveScoreCard") RedisTemplate<String, RapidScoreCardDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RapidScoreCardDto getScoreFromCache(String matchId) {
        return redisTemplate.opsForValue().get(matchId);
    }

    public void saveScoreToCache(String matchId, RapidScoreCardDto score) {
        redisTemplate.opsForValue().set(matchId, score, 2, TimeUnit.MINUTES);  // Cache for 10 minutes
    }
}
