package com.cricketService.services.match;

import com.cricketService.dto.matchList.MatchListRapid;
import com.cricketService.dto.matchList.typeMatches.TypeMatches;
import com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.SeriesAdWrapper;
import com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.matches.matchInfo.MatchInfo;
import com.cricketService.dto.scheduler.MatchIdAndStartDate;
import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.enums.MatchStatus;
import com.cricketService.repo.schedule.UpcomingMatchListRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Set<Long> matchId = getUpcoming.stream().map(UpcomingMatchList::getMatchId).collect(Collectors.toSet());
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
                                    UpcomingMatchList list = getUpcomingMatchList(typeMatches, matches.getMatchInfo());
                                    if (!matchId.contains(list.getMatchId())) {
                                        filteredList.add(list);
                                    }
                                    if (list.getStartDate().compareTo(currentDatePlus24Hours) <= 0) {
                                        matchesL.add(new MatchIdAndStartDate(list.getMatchId(), list.getStartDate(), list.getSeriesName()));
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        upcomingMatchListRepo.saveAll(filteredList);
        return matchesL;
    }

    @Transactional
    public UpcomingMatchList findById(Long id) {
        return upcomingMatchListRepo.findById(id).orElse(null);
    }

    @Transactional
    public UpcomingMatchList findByMatchId(long matchId){
        return upcomingMatchListRepo.findByMatchId(matchId).orElse(null);
    }

    @Transactional
    public void removeUpcomingMatchById(long id){
        upcomingMatchListRepo.deleteById(id);
    }

    @Transactional
    public List<UpcomingMatchList> findUpcomingMatchList(){
        return upcomingMatchListRepo.findAll();
    }


    private UpcomingMatchList getUpcomingMatchList(TypeMatches typeMatches, MatchInfo matchInfo) {
        UpcomingMatchList list = new UpcomingMatchList();
        list.setMatchId(matchInfo.getMatchId());
        list.setSeriesId(matchInfo.getSeriesId());
        list.setMatchType(typeMatches.getMatchType());
        list.setStartDate(Long.parseLong(matchInfo.getStartDate()));
        list.setEndDate(Long.parseLong(matchInfo.getEndDate()));
        list.setSeriesName(matchInfo.getSeriesName());
        return list;
    }

}