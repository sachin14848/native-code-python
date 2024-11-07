package com.cricketService.config.redis;

import com.cricketService.dto.ScoreDto;
import com.cricketService.redis.Receiver;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MessageConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

//    @Bean
//    public JedisConnectionFactory connectionFactory(){
//        RedisStandaloneConfiguration configuration =new RedisStandaloneConfiguration();
//        configuration.setHostName("localhost");
//        configuration.setPort(6379);
//        return new JedisConnectionFactory(configuration);
//    }

    @Bean(name = "pubSub")
    public RedisTemplate<String, ScoreDto> redisTemplatePubSub(RedisConnectionFactory factory){
        RedisTemplate<String, ScoreDto> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // Configure ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson2JsonRedisSerializer<ScoreDto> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, ScoreDto.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }

    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("pubSub:liveScore");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    public RedisMessageListenerContainer messageListenerContainer(RedisConnectionFactory factory,
                                                                  MessageListenerAdapter adapter,
                                                                  ChannelTopic topic
                                                                  ){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(adapter, topic);
        return container;
    }


}
