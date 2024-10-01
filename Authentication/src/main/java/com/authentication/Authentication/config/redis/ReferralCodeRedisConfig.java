package com.authentication.Authentication.config.redis;

import com.authentication.Authentication.entities.user.ReferralUsage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ReferralCodeRedisConfig {

    @Bean
    public RedisTemplate<String, ReferralUsage> referralUserRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, ReferralUsage> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
