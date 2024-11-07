package com.question_generator.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.question_generator.dto.ScoreDto;
import com.question_generator.services.cricket.GenerateQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver implements MessageListener {


    private final Jackson2JsonRedisSerializer<ScoreDto> serializer;
    private final GenerateQuestion generateQuestion;

    @Autowired
    public Receiver(GenerateQuestion generateQuestion) {
        this.generateQuestion = generateQuestion;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, ScoreDto.class);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ScoreDto scoreDto = serializer.deserialize(message.getBody());
        generateQuestion.createQuestion(scoreDto.getScoreCard());
        log.info("Consumed event {}", scoreDto);
    }
}