package com.cricketService.utils;

import com.cricketService.rabbhitMqSevices.WebSocketPublisherService;
import com.cricketService.rabbhitMqSevices.rabbitMqDto.ScoreMessageDto;
import com.cricketService.redis.RedisPublish;
import com.cricketService.services.test.CricketScore;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SampleJob implements Job {

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisPublish publish;

    @Autowired
    public SampleJob(SimpMessagingTemplate messagingTemplate, RedisPublish publish) {
        this.messagingTemplate = messagingTemplate;
        this.publish = publish;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Executing Quartz job at {}", context.getFireTime());
        String matchId = context.getJobDetail().getKey().getName();
        publish.publishMessage(matchId);
    }

    public void sendScoreUpdate(String matchId, CricketScore score) {
        messagingTemplate.convertAndSend("/topic/live-score/" + matchId, score);
    }

}
