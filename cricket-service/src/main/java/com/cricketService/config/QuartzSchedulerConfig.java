//package com.cricketService.config;
//
//import com.cricketService.utils.SampleJob;
//import lombok.RequiredArgsConstructor;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@RequiredArgsConstructor
//@Configuration
//public class QuartzSchedulerConfig {
//
//
//    private final SampleJob sampleJob;
//
//    @Bean
//    public JobDetail sampleJobDetail() {
//        return JobBuilder.newJob(SampleJob.class)
//                .withIdentity("sampleJob")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger sampleJobTrigger() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(30)
//                .repeatForever();
//
//        return TriggerBuilder.newTrigger()
//                .forJob(sampleJobDetail())
//                .withIdentity("sampleJobTrigger")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
//}
