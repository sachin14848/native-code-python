package com.cricketService.services;

import com.cricketService.dto.RapidApiLiveScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatchLiveScoreRedisTemplateService {

    @Autowired
    private RedisTemplate<String, RapidApiLiveScore> redisTemplate;



}
