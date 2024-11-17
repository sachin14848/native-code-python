package com.order.order.services.redis;

import com.order.order.dto.PriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class PriceUpdatePublish {

    private final RedisTemplate<String, PriceDto> priceUpdateTemplate;
    private final ChannelTopic topic;

    @Autowired
    public PriceUpdatePublish(@Qualifier("priceLive") RedisTemplate<String, PriceDto> priceUpdateTemplate, ChannelTopic topic) {
        this.priceUpdateTemplate = priceUpdateTemplate;
        this.topic = topic;
    }

    public void handlePriceUpdate(PriceDto priceDto) {
        System.out.println("Received price update: " + priceDto);
        priceUpdateTemplate.convertAndSend(topic.getTopic(), priceDto);
        // Perform any necessary logic here, such as updating a database or triggering a notification
    }


}
