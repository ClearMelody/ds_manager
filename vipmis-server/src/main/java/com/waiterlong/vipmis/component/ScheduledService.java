package com.waiterlong.vipmis.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 * @author walter_long
 */
@Component
@EnableScheduling
@EnableAsync
public class ScheduledService {

    private Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    /**
     * 每1个半小时获取一次accessToken
     */
    @Async
//    @Scheduled(cron = "0 0 0/3 * * ?")
//    @Scheduled(cron = "0 30 1/3 * * ?")
    public void getAndUpdateAccessToken() {
    }

}
