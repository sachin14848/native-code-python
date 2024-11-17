package com.order.order.services.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.order.order.dto.PriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PriceUpdateReceiver implements MessageListener {

    private final Jackson2JsonRedisSerializer<PriceDto> serializer;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PriceUpdateReceiver(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, PriceDto.class);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        PriceDto priceDto = serializer.deserialize(message.getBody());
        assert priceDto != null;
        messagingTemplate.convertAndSend("/topic/order/price/" + priceDto.getOrderId(), priceDto);
    }
}
