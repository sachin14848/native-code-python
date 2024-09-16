package com.cricketService.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisService {

//    private final RedisTemplate<String, Object> redisTemplate;

//    public <T> T get(String key, Class<T> value) {
//
//        try {
//            log.info("getting Key {}", key);
//            Object o = redisTemplate.opsForValue().get(key);
//            ObjectMapper mapper = new ObjectMapper();
//            assert o != null;
//            return mapper.readValue(o.toString(), value);
//        } catch (Exception e) {
//            log.error("Error while getting data from Redis: {}", e.getMessage());
//            return null;
//        }
//    }
//
//    public void set(String key, Object o, Long timeout) {
//
//        try {
//            log.info("Setting Key {} with value {}", key, o);
//            redisTemplate.opsForValue().set(key, o.toString(), timeout, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            log.error("Error while setting data from Redis: {}", e.getMessage());
//        }
//    }


}
