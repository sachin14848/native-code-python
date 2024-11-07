package com.cricketService.redis;

import com.cricketService.dto.ScoreDto;
import com.cricketService.services.scoreCard.ScoreCardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisPublish {

    private final RedisTemplate<String, ScoreDto> redisTemplate;
    private final ChannelTopic topic;
    private final ScoreCardService scoreCardService;

    @Autowired
    public RedisPublish(@Qualifier("pubSub") RedisTemplate<String, ScoreDto> redisTemplate, ChannelTopic topic, ScoreCardService scoreCardService){
        this.redisTemplate = redisTemplate;
        this.topic = topic;
        this.scoreCardService = scoreCardService;
    }

    public void publishMessage(String matchId){
        ScoreDto scoreDto = scoreCardService.getMatchLiveScore(matchId);
        scoreDto.setPublishedBy(this.getClass().getSimpleName());
        log.info("Message is published: {}", scoreDto);
        redisTemplate.convertAndSend(topic.getTopic(), scoreDto);
    }

}
