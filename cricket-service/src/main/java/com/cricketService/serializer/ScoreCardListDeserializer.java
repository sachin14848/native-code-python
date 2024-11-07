package com.cricketService.serializer;

import com.cricketService.dto.score.ScoreCard;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreCardListDeserializer extends JsonDeserializer<List<ScoreCard>> {
    @Override
    public List<ScoreCard> deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JsonNode node = mapper.readTree(parser);

        List<ScoreCard> scoreCards = new ArrayList<>();
        if (node.isArray()) {
            for (JsonNode jsonNode : node) {
                ScoreCard scoreCard = mapper.treeToValue(jsonNode, ScoreCard.class);
                scoreCards.add(scoreCard);
            }
        }
        return scoreCards;
    }
}