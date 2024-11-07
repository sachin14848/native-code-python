package com.cricketService.scheduleJobServies;

import com.cricketService.redis.RedisPublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LiveScoreJobScheduler implements Job {


    private final RedisPublish publish;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String matchIdStr = context.getJobDetail().getKey().getName();
        log.info("Live Score Job {}", matchIdStr);
        publish.publishMessage(matchIdStr);
    }


}