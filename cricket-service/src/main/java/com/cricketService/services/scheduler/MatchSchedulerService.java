package com.cricketService.services.scheduler;

import com.cricketService.scheduleJobServies.LiveMatchScheduleJob;
import com.cricketService.scheduleJobServies.UpcomingMatchScheduleJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class MatchSchedulerService {

    private final Scheduler scheduler;

    public void scheduleUpcomingMatches(String jobName, String jobGroup) throws SchedulerException {
        if (isJobScheduled(jobName, jobGroup)) {
            return;
        }

        JobDetail jobDetail = JobBuilder.newJob(UpcomingMatchScheduleJob.class)
                .withIdentity(jobName, jobGroup)
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "_Trigger", jobGroup)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 0))  //CronScheduleBuilder.dailyAtHourAndMinute(10, 0),.  CronScheduleBuilder.cronSchedule("0 * * ? * *")
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public void scheduleLiveMatches(String jobName, String jobGroup, long delayInMilliseconds, String des) throws SchedulerException {
        if (isJobScheduled(jobName, jobGroup)) {
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(LiveMatchScheduleJob.class)
                .withIdentity(jobName, jobGroup)
                .withDescription(des)
                .build();

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "_Trigger", jobGroup)
                .startAt(new Date(delayInMilliseconds))   //DateBuilder.futureDate((int) delayInMilliseconds, DateBuilder.IntervalUnit.MILLISECOND)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(0))
                .build();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }


    public boolean isJobScheduled(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);

        if (scheduler.checkExists(jobKey)) {
            System.out.println("Job already exists: " + jobKey);
            return true;
        }
        return false;
    }

}