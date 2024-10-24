package com.cricketService.utils;

import com.cricketService.services.test.CricketScore;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SampleJob implements Job {

    private final SimpMessagingTemplate messagingTemplate;

    public SampleJob(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Executing Quartz job at {}", context.getFireTime());
//        sendScoreUpdate("100", new CricketScore("kjjksdkfh"));
    }

    public void sendScoreUpdate(String matchId, CricketScore score) {
        messagingTemplate.convertAndSend("/topic/live-score/" + matchId, score);
    }

}
