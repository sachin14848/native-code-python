package com.cricketService.scheduleJobServies;

import com.cricketService.services.match.LiveMatchScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LiveMatchScheduleJob implements Job {

    private final LiveMatchScheduleService liveMatchScheduleService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Executing Live Match");
        final long matchId = Long.parseLong(context.getJobDetail().getKey().getName());
        liveMatchScheduleService.listLiveMatches(matchId);

    }
}
