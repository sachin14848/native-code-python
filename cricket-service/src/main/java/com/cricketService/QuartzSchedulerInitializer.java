package com.cricketService;

import com.cricketService.services.scheduler.MatchSchedulerService;
import com.cricketService.services.scoreCard.ScoreCardService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QuartzSchedulerInitializer {

    private final MatchSchedulerService matchSchedulerService;
    private final ScoreCardService scoreCardService;

    @PostConstruct
    public void scheduleJobs() throws SchedulerException {
        matchSchedulerService.scheduleUpcomingMatches("upcomingMatches", "matches");
//        scoreCardService.getMatchLiveScore(108425);
    }

}
