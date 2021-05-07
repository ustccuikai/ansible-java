package com.ansbile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling //启用定时任务
public class ScheduleTask {
    private final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    // 每1分钟执行一次,调度中断的部署任务
    @Scheduled(cron = "0 0/1 * * * ?")
    public void deployTaskSchedule() {
    }
}
