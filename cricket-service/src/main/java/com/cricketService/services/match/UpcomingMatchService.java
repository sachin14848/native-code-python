package com.cricketService.services.match;

import com.cricketService.dto.match.MatchInfoDto;
import com.cricketService.dto.matchList.MatchListRapid;
import com.cricketService.dto.matchList.typeMatches.TypeMatches;
import com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.SeriesAdWrapper;
import com.cricketService.dto.scheduler.MatchIdAndStartDate;
import com.cricketService.dto.upcoming.UpComingMatchDto;
import com.cricketService.entities.match.MatchInfo;
import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.enums.MatchStatus;
import com.cricketService.enums.MatchType;
import com.cricketService.repo.schedule.UpcomingMatchListRepo;
import com.cricketService.services.MatchService;
import com.cricketService.services.scheduler.MatchSchedulerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpcomingMatchService {

    private final RestTemplate restTemplate;
    private final UpcomingMatchListRepo upcomingMatchListRepo;
    private final MatchService matchService;
    private final MatchSchedulerService matchSchedulerService;


    @Value("${rapid.baseURL}")
    private String baseUrl;

    @Value("${rapid.urls.matches}")
    private String matches;

    @Transactional
    public Set<MatchIdAndStartDate> fetchUpcomingMatches() {
        final String url = baseUrl + matches + MatchStatus.upcoming.name();
        log.info("URL {}", url);
        MatchListRapid seriesList = null;
        try {
            seriesList = restTemplate.getForObject(url, MatchListRapid.class);
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
        List<UpcomingMatchList> getUpcoming = upcomingMatchListRepo.findAll();
        Set<Long> matchId = getUpcoming.stream().map(upcomingMatchList ->
                        upcomingMatchList.getMatchId().getId())
                .collect(Collectors.toSet());
        long currentDateInMillis = new Date().getTime();
        long millisIn24Hours = 24 * 60 * 60 * 1000;
        long currentDatePlus24Hours = currentDateInMillis + millisIn24Hours;
        Set<UpcomingMatchList> filteredList = new HashSet<>();
        Set<MatchIdAndStartDate> matchesL = new HashSet<>();
        seriesList.getTypeMatches().forEach(typeMatches -> {
            if (typeMatches != null) {
                typeMatches.getSeriesMatches().forEach(seriesMatches -> {
                    if (seriesMatches != null) {
                        SeriesAdWrapper series = seriesMatches.getSeriesAdWrapper();
                        if (series != null) {
                            series.getMatches().forEach(matches -> {
                                if (matches != null) {
                                    Long id = matches.getMatchInfo().getMatchId();
                                    if (!matchId.contains(id)) {
                                        MatchInfo matchInfo = matchService.getMatchInfoByMatchIds(id);
                                        if (matchInfo != null) {
                                            UpcomingMatchList list = getUpcomingMatchList(typeMatches, matchInfo);
                                            filteredList.add(list);
                                        } else {
                                            matchService.createMatchInfoBySeriesId(matches.getMatchInfo().getSeriesId());
                                            MatchInfo matchInfo1 = matchService.getMatchInfoByMatchIds(id);
                                            if (matchInfo1 != null) {
                                                UpcomingMatchList list = getUpcomingMatchList(typeMatches, matchInfo1);
                                                filteredList.add(list);
                                            }
                                        }
                                    }
                                    long startDateMillis = Long.parseLong(matches.getMatchInfo().getStartDate());
                                    try {
                                        boolean isSchedule = matchSchedulerService.isJobScheduled(id.toString(), MatchStatus.live.name());
                                        if (!isSchedule && startDateMillis >= currentDateInMillis && startDateMillis <= currentDatePlus24Hours) {
                                            matchesL.add(new MatchIdAndStartDate(matches.getMatchInfo().getMatchId(), Long.parseLong(matches.getMatchInfo().getStartDate()), matches.getMatchInfo().getSeriesName()));
                                        }
                                    } catch (SchedulerException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                        }
                    }
                });
            }
        });
        if (!filteredList.isEmpty()) {
            upcomingMatchListRepo.saveAll(filteredList);
        }
        return matchesL;
    }

    @Transactional
    public UpcomingMatchList findById(Long id) {
        return upcomingMatchListRepo.findById(id).orElse(null);
    }

    @Transactional
    public UpcomingMatchList findByMatchId(long matchId) {
        return upcomingMatchListRepo.findByMatchId_Id(matchId).orElse(null);
    }

    @Transactional
    public void removeUpcomingMatchById(long id) {
        upcomingMatchListRepo.deleteById(id);
    }

    @Transactional
    public List<UpcomingMatchList> findUpcomingMatchList() {
        return upcomingMatchListRepo.findAll();
    }

    @Transactional
    public List<UpComingMatchDto> findUpcomingMatchListByMatchType(MatchType matchType) {
        return upcomingMatchListRepo.findByMatchType(matchType).stream()
                .filter(upcomingMatchList -> upcomingMatchList.getMatchType().equals(matchType))
                .map(upcomingMatchList -> UpComingMatchDto.builder()
                        .matchInfo(upcomingMatchList.getMatchId())
                        .startDate(upcomingMatchList.getStartDate())
                        .seriesName(upcomingMatchList.getSeriesName())
                        .build())
                .collect(Collectors.toList());
    }


    private UpcomingMatchList getUpcomingMatchList(TypeMatches typeMatches, MatchInfo matchInfo) {
        UpcomingMatchList list = new UpcomingMatchList();
        list.setMatchId(matchInfo);
        list.setSeriesId(matchInfo.getSeries().getId());
        list.setMatchType(typeMatches.getMatchType());
        list.setStartDate(Long.parseLong(matchInfo.getStartDate()));
        list.setEndDate(Long.parseLong(matchInfo.getEndDate()));
        list.setSeriesName(matchInfo.getSeriesName());
        return list;
    }

}