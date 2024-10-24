package com.cricketService.config;

import com.cricketService.dto.RapidApiLiveScore;
import com.cricketService.dto.activeUser.LiveMatchActiveUserDto;
import com.cricketService.dto.scoreBoard.RapidScoreCardDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

@Configuration
public class RedisConfig {


//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        Jackson2JsonRedisSerializer<Object> serial = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//        serial.serialize(objectMapper);
//
//        // Use StringRedisSerializer for the key
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(serial);
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(serial);
//        return redisTemplate;
//    }

    @Bean(name = "matchLiveScore")
//    @Bean
    public RedisTemplate<String, RapidApiLiveScore> liveScoreRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, RapidApiLiveScore> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean(name = "liveScoreCard")
    public RedisTemplate<String, RapidScoreCardDto> scoreCardRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, RapidScoreCardDto> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean(name = "liveMatchActiveUser")
    public RedisTemplate<String, Map<String, String>> liveMatchActiveUserRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Map<String, String>> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
