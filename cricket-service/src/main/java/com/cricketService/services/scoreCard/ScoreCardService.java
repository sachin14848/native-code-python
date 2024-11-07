package com.cricketService.services.scoreCard;

import com.cricketService.dto.PublishScoreCard;
import com.cricketService.dto.RapidApiScoreCardData;
import com.cricketService.dto.ScoreDto;
import com.cricketService.dto.score.ScoreCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScoreCardService {

    private final RestTemplate restTemplate;

    @Value("${rapid.baseURL}")
    private String baseUrl;

    @Value("${rapid.urls.scoreCard}")
    private String liveScoreCardUrl;

    public ScoreDto getMatchLiveScore(String matchId) {
        try {
            final String url = baseUrl + liveScoreCardUrl + matchId + "/hscard";
            RapidApiScoreCardData score = restTemplate.getForObject(url, RapidApiScoreCardData.class);
            log.info("Score: {}", score);
            assert score != null;
            return getScoreDto(score);
        } catch (Exception e) {
            log.error("Error fetching live score: {}", e.getMessage());
            return null;
        }
    }


    private ScoreDto getScoreDto(RapidApiScoreCardData data) {
        ScoreCard[] list = data.getScoreCard().length > 0 ? data.getScoreCard() : null;
        List<ScoreCard> scores = list != null ? Arrays.stream(list)
                .collect(Collectors.toCollection(ArrayList::new)) : new ArrayList<>();
        PublishScoreCard datas = PublishScoreCard.builder()
                .scoreCards(scores)
                .matchHeader(data.getMatchHeader())
                .build();
        return ScoreDto.builder()
                .ScoreCard(datas)
                .build();
    }

}
