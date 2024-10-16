package com.cricketService.services.match;

import com.cricketService.entities.schedule.LiveMatchList;
import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.repo.schedule.LiveMatchListRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LiveMatchScheduleService {

    private final LiveMatchListRepo liveMatchListRepo;
    private final UpcomingMatchService upcomingMatchService;

    public void listLiveMatches(Long matchId) {
        final UpcomingMatchList upcomingMatchList = upcomingMatchService.findByMatchId(matchId);
        liveMatchListRepo.save(
                LiveMatchList.builder()
                        .matchType(upcomingMatchList.getMatchType())
                        .seriesId(upcomingMatchList.getSeriesId())
                        .matchId(upcomingMatchList.getMatchId())
                        .build());

        upcomingMatchService.removeUpcomingMatchById(upcomingMatchList.getId());
    }

}
