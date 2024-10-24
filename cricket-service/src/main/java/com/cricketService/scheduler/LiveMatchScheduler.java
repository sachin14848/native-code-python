package com.cricketService.scheduler;

import com.cricketService.scheduleJobServies.LiveMatchScheduleJob;
import com.cricketService.scheduleJobServies.LiveScoreJobScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class LiveMatchScheduler {

    private final Scheduler scheduler;
    private final String JOB_GROUP = "liveScore";

    public void scheduleLiveMatches(String jobName, String des) throws SchedulerException {
        if(isJobScheduled(jobName)){
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(LiveScoreJobScheduler.class)
                .withIdentity(jobName, JOB_GROUP)
                .withDescription(des)
                .storeDurably()
                .build();

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "_Trigger", JOB_GROUP)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)  // Set interval in seconds
                        .repeatForever())  // Repeat the job forever
                .build();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }

    public void shutdownJob(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, JOB_GROUP);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    public void pauseJob(String jobName) throws SchedulerException {
        log.info("Push the job to the scheduler");
        JobKey jobKey = new JobKey(jobName, JOB_GROUP);
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
        }
    }

    public void resumeJob(String jobName) throws SchedulerException {
        log.info("Resume Job the job to the scheduler");
        JobKey jobKey = new JobKey(jobName, JOB_GROUP);
        if (scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
        }
    }

    public boolean isJobRunning(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, JOB_GROUP);
        if (scheduler.checkExists(jobKey)) {
            for (Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                if (triggerState == Trigger.TriggerState.NORMAL) {
                    log.info("Job {} is running.", jobName);
                    return true;
                }
            }
            log.info("Job {} is not running.", jobName);
        } else {
            log.info("Job {} does not exist in is Running.", jobName);
        }
        return false;
    }

    public boolean isJobPaused(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, JOB_GROUP);
        if (scheduler.checkExists(jobKey)) {
            for (Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                if (triggerState == Trigger.TriggerState.PAUSED) {
                    log.info("Job {} is paused.", jobName);
                    return true;
                }
            }
            log.info("Job {} is not paused.", jobName);
        } else {
            log.info("Job {} does not exist.", jobName);
        }
        return false;
    }


    public boolean isJobScheduled(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, JOB_GROUP);
        return scheduler.checkExists(jobKey);
    }

}
