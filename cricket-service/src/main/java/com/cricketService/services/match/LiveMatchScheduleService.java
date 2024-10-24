package com.cricketService.services.match;

import com.cricketService.dto.match.LiveMatchListDto;
import com.cricketService.entities.match.MatchInfo;
import com.cricketService.entities.schedule.LiveMatchList;
import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.repo.schedule.LiveMatchListRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LiveMatchScheduleService {

    private final LiveMatchListRepo liveMatchListRepo;
    private final UpcomingMatchService upcomingMatchService;

    public void listLiveMatches(Long matchId) {
        final UpcomingMatchList upcomingMatchList = upcomingMatchService.findByMatchId(matchId);
        MatchInfo matchInfo = upcomingMatchList.getMatchId();
        liveMatchListRepo.save(
                LiveMatchList.builder()
                        .matchType(upcomingMatchList.getMatchType())
                        .seriesId(upcomingMatchList.getSeriesId())
                        .matchId(upcomingMatchList.getMatchId())
                        .build());

        upcomingMatchService.removeUpcomingMatchById(upcomingMatchList.getId());
    }

    public List<LiveMatchListDto> getLiveMatchList(){
        return liveMatchListRepo.findAll()
               .stream()
               .map(LiveMatchListDto::new)
               .toList();
    }

    public boolean checkIfMatchExistsById(Long matchId) {
        return liveMatchListRepo.existsByMatchId_Id(matchId);
    }

}
