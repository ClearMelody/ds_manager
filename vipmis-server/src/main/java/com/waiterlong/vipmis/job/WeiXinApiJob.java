package com.waiterlong.vipmis.job;

import com.waiterlong.vipmis.service.IWeiXinApiService;
import com.waiterlong.vipmis.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 定时任务类
 * @author walter_long
 */
@Component
@EnableScheduling
@EnableAsync
public class WeiXinApiJob {
    private Logger logger = LoggerFactory.getLogger(WeiXinApiJob.class);

    @Resource(name="iWeiXinApiService")
    private IWeiXinApiService iWeiXinApiService;
    /**
     * 每半小时获取一次accessToken
     */
    @Async
    @Scheduled(cron = "0 0/20 * * * ?")
    public void updateAccessToken() {
        logger.info("update accessToken begin : " + DateUtil.dateToString(new Date()));
        String msg = iWeiXinApiService.updateAccessToken().getErrmsg();
        logger.info("update accessToken end : " + DateUtil.dateToString(new Date()) + "  msg:" + msg);
    }
}
