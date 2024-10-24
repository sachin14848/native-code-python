package com.cricketService.scheduleJobServies;

import com.cricketService.dto.RapidApiLiveScore;
import com.cricketService.services.MatchLiveScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LiveScoreJobScheduler implements Job {

    private final SimpMessagingTemplate messagingTemplate;
    private final MatchLiveScoreService matchLiveScoreService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String matchIdStr = context.getJobDetail().getKey().getName();
        int matchId = Integer.parseInt(matchIdStr);
        log.info("Live Score Job {}", matchId);
        RapidApiLiveScore score = matchLiveScoreService.getMatchLiveScore(matchId);
        sendScoreUpdate(matchIdStr, score);
    }

    public void sendScoreUpdate(String matchId, RapidApiLiveScore score) {
        messagingTemplate.convertAndSend("/topic/live-score/" + matchId, score);
    }
}