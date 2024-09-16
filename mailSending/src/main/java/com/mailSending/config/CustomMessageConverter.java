package com.mailSending.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mailSending.utiles.CustomDeserializer;
import com.mailSending.utiles.CustomSerializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class CustomMessageConverter implements MessageConverter {

    private final ObjectMapper objectMapper;

    public CustomMessageConverter() {
        this.objectMapper = new ObjectMapper();
        // Register the custom serializer and deserializer
        SimpleModule module = new SimpleModule();
        module.addSerializer(SimpleMailMessage.class, new CustomSerializer());
        module.addDeserializer(SimpleMailMessage.class, new CustomDeserializer());
        objectMapper.registerModule(module);
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) {
        if (object instanceof SimpleMailMessage) {
            try {
                // Serialize SimpleMailMessage to JSON
                byte[] body = objectMapper.writeValueAsBytes(object);
                return new Message(body, messageProperties);
            } catch (IOException|MessageConversionException e) {
                throw new RuntimeException("Failed to serialize message", e);
            }
        }else if (object instanceof String) {
            // Handle String messages
            byte[] body = ((String) object).getBytes(StandardCharsets.UTF_8);
            return new Message(body, messageProperties);
        }
        throw new IllegalArgumentException("Unsupported message object: " + object.getClass());
    }

    @Override
    public Object fromMessage(Message message) {
        try {
            // Deserialize JSON to SimpleMailMessage
            return objectMapper.readValue(message.getBody(), SimpleMailMessage.class);
        } catch (IOException|MessageConversionException e) {
            System.err.println("Failed to deserialize message: " + new String(message.getBody()));
            throw new RuntimeException("Failed to deserialize message", e);
        }
    }

}
