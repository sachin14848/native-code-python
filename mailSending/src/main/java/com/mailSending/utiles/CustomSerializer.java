package com.mailSending.utiles;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;
import java.util.Objects;

public class CustomSerializer extends JsonSerializer<SimpleMailMessage> {
    @Override
    public void serialize(SimpleMailMessage value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeArrayFieldStart("to");
        for (String recipient : Objects.requireNonNull(value.getTo())) {
            gen.writeString(recipient);
        }
        gen.writeEndArray();
        gen.writeStringField("subject", value.getSubject());
        gen.writeStringField("text", value.getText());
//        gen.writeStringField("from", value.getFrom());
        gen.writeEndObject();
    }
}
