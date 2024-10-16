package com.cricketService.scheduleJobServies;

import com.cricketService.dto.scheduler.MatchIdAndStartDate;
import com.cricketService.services.match.UpcomingMatchService;
import com.cricketService.services.scheduler.MatchSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpcomingMatchScheduleJob implements Job {

    private final MatchSchedulerService matchSchedulerService;
    private final UpcomingMatchService upcomingMatchService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("Executing daily task at 10:00 AM {}", context.getJobDetail().getKey().getName());
//        final int[] i = {10};  /// it for testing purposes
//        long dd = now[0] + 2000L * i[0];
//        i[0] = i[0] + 10;
        try {
            final long[] now = {new Date().getTime()};
            Set<MatchIdAndStartDate> matches = upcomingMatchService.fetchUpcomingMatches();
            if (!matches.isEmpty()) {

                matches.forEach(match -> {
                    try {
                        matchSchedulerService.scheduleLiveMatches(
                                match.getMatchId().toString(), "LiveMatch", match.getStartDate(), match.getSeriesName());
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("Failed to schedule millisecond task", e);
        }
    }
}
