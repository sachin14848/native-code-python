//package com.authentication.Authentication.question.redis;
//
//import com.authentication.Authentication.question.services.cricket.GenerateQuestion;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class Receiver implements MessageListener {
//
//
//    private final Jackson2JsonRedisSerializer<ScoreDto> serializer;
//    private final GenerateQuestion generateQuestion;
//
//    @Autowired
//    public Receiver(GenerateQuestion generateQuestion) {
//        this.generateQuestion = generateQuestion;
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, ScoreDto.class);
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        ScoreDto scoreDto = serializer.deserialize(message.getBody());
//        assert scoreDto != null;
//        generateQuestion.createQuestion(scoreDto.getScoreCard());
//        log.info("Consumed event {}", scoreDto);
//    }
//
//}