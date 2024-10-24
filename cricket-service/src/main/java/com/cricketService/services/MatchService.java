package com.cricketService.services;

import com.cricketService.dto.match.*;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        MatchRapidApi seriesList = getMatchRapidApiData(url, MatchRapidApi.class);
        if (!seriesService.isSeriesSaved(Long.parseLong(matchId))) {
            assert seriesList != null;
            saveMatchInfo(seriesList.getMatchDetails());
        } else {
            log.info("Series saved successfully");
            Set<Integer> matchInfoDto = getMatchInfoBySeriesId(Integer.parseInt(matchId)).stream().map(MatchInfoDto::getMatchId).collect(Collectors.toSet());
            assert seriesList != null;
            log.info("Series Saved  : {}", matchInfoDto);
            saveMatchInfoInExistingSeries(seriesList.getMatchDetails(), matchInfoDto);
        }
        return seriesList;
    }

    @Transactional
    public void createMatchInfoBySeriesId(Long seriesId) {
        final String url = baseUrl + matchDetailsUrl + seriesId;
        log.info("URL {}", url);
        MatchRapidApi seriesList = getMatchRapidApiData(url, MatchRapidApi.class);
//        if (!seriesService.isSeriesSaved(seriesId)) {
            assert seriesList != null;
            saveMatchInfo(seriesList.getMatchDetails());
//        } else {
//            log.info("Series saved successfully fg");
//        }
    }

    public List<MatchInfoDto> getMatchInfoBySeriesId(int id) {
        return matchInfoRepository.findBySeries_Id(id).stream().map(MatchInfoDto::new).toList();
    }

    private List<MatchInfo> getMatchesBySeriesId(int id) {
        return matchInfoRepository.findBySeries_Id(id);
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

    public MatchInfo getMatchInfoByMatchIds(Long id) {
        Optional<MatchInfo> matchInfo = matchInfoRepository.findById(id);
        return matchInfo.orElse(null);
    }


    private <T> T getMatchRapidApiData(String url, Class<T> responseType) {
        try {
            return restTemplate.getForObject(url, responseType);
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
    }

    private void saveMatchInfo(List<MatchDetails> matchDetail) {
        matchDetail.forEach(matchDetails -> {
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
    }

    private void saveMatchInfoInExistingSeries(List<MatchDetails> matchDetail, Set<Integer> existingSeriesMatches) {
        matchDetail.forEach(matchDetails -> {
            MatchDetailsMap matchDetailsMap = matchDetails.getMatchDetailsMap();
            if (matchDetailsMap != null) {
                List<Match> matches = matchDetailsMap.getMatch();
                if (matches != null) {
                    matches.forEach(match -> {
                        MatchInfoDto dd = match.getMatchInfo();
                       if(!existingSeriesMatches.contains(dd.getMatchId())){
                           log.info("Match Id : {}", dd.getMatchId());
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
                       }
                    });
                }
            }
        });
    }


}
