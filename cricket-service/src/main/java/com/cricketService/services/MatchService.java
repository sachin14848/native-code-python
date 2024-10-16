package com.cricketService.services;

import com.cricketService.dto.match.Match;
import com.cricketService.dto.match.MatchDetailsMap;
import com.cricketService.dto.match.MatchInfoDto;
import com.cricketService.dto.match.MatchRapidApi;
import com.cricketService.entities.match.MatchInfo;
import com.cricketService.repo.match.MatchInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@EnableCaching
public class MatchService {

    private final RestTemplate restTemplate;
    private final MatchInfoRepository matchInfoRepository;
    //    private final MatchRapidApi matchApi;
    private final SeriesService seriesService;
    private final TeamListService teamListService;
//    private final RedisService redisService;

    @Value("${rapid.baseURL}")
    private String baseUrl;

    @Value("${rapid.urls.matchDetailsUrl}")
    private String matchDetailsUrl;

    @Transactional
    public MatchRapidApi createMatch(String matchId) {
        log.info("Creating Match matchId {}", matchId);

        final String url = baseUrl + matchDetailsUrl + matchId;
        log.info("URL {}", url);
        MatchRapidApi seriesList = null;
        try {
            seriesList = restTemplate.getForObject(url, MatchRapidApi.class);
        } catch (ResourceAccessException e) {
            log.error("Error fetching data from Rapid API: {}", e.getMessage());
            return null;
        } catch (RestClientException e) {
            log.error("Error fetching 12 data from Rapid API: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error("Error fetching data from Rapid API: {}", e.getMessage(), e);
            return null;
        }

        assert seriesList != null;
        seriesList.getMatchDetails().forEach(matchDetails -> {
            MatchDetailsMap matchDetailsMap = matchDetails.getMatchDetailsMap();
            if (matchDetailsMap != null) {
                List<Match> matches = matchDetailsMap.getMatch();
                if (matches != null) {
                    matches.forEach(match -> {
                        MatchInfoDto dd = match.getMatchInfo();
                        MatchInfo matchInfo = MatchInfo.builder()
                                .id((long) dd.getMatchId())
                                .series(seriesService.getSingleSeriesSById(dd.getSeriesId()))
                                .matchFormat(dd.getMatchFormat())
                                .matchDesc(dd.getMatchDesc())
                                .currBatTeamId(dd.getCurrBatTeamId())
                                .endDate(dd.getEndDate())
                                .startDate(dd.getStartDate())
                                .state(dd.getState())
                                .status(dd.getStatus())
                                .team1(teamListService.getTeams(dd.getTeam1()))
                                .team2(teamListService.getTeams(dd.getTeam2()))
                                .isTimeAnnounced(dd.isTimeAnnounced())
                                .build();
                        matchInfoRepository.save(matchInfo);
                    });
                }
            }
        });

        return seriesList;
    }

    public List<MatchInfoDto> getMatchInfoBySeriesId(int id) {
        return matchInfoRepository.findBySeries_Id(id).stream().map(MatchInfoDto::new).toList();
    }

    //    @Cacheable(value = "matchInfoBySeries", key = "#id")
    public MatchInfoDto getMatchInfoByMatchId(Long id) {
//        MatchInfoDto test = redisService.get(id.toString(), MatchInfoDto.class);
//        if (test != null) {
//            return test;
//        }else {
        return matchInfoRepository.findById(id).map(MatchInfoDto::new).orElseThrow(() -> new RuntimeException("No match found"));
//            redisService.set(id.toString(), se, 300L);
//        return se;
//        }
    }
}
