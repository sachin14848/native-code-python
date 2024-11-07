package com.cricketService.redis;

import com.cricketService.dto.ScoreDto;
import com.cricketService.utils.CricketScoreUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

//@RequiredArgsConstructor
@Slf4j
@Component
public class Receiver implements MessageListener {

    private final Jackson2JsonRedisSerializer<ScoreDto> serializer;
    private final CricketScoreUtils cricketScoreUtils;

    @Autowired
    public Receiver(CricketScoreUtils cricketScoreUtils) {
        this.cricketScoreUtils = cricketScoreUtils;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, ScoreDto.class);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ScoreDto scoreDto = serializer.deserialize(message.getBody());
            log.info("onMessage received score: {}", scoreDto);
             cricketScoreUtils.sendScoreCardUpdate(scoreDto);
        } catch (Exception e) {
            log.error("Error deserializing message: {}", e.getMessage(), e);
        }
    }

}
