package com.mailSending.utiles;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;

public class CustomDeserializer extends JsonDeserializer<SimpleMailMessage> {
    @Override
    public SimpleMailMessage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(node.get("to").traverse(p.getCodec()).readValueAs(String[].class));
        message.setSubject(node.get("subject").asText());
        message.setText(node.get("text").asText());
//        message.setFrom(node.get("from").asText());

        return message;
    }
}
