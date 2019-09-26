package com.waiterlong.vipmis.component;

import com.waiterlong.vipmis.job.WeiXinApiJob;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/9/9
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        WeiXinApiJob weiXinApiJob = contextRefreshedEvent.getApplicationContext().getBean(WeiXinApiJob.class);
        weiXinApiJob.updateAccessToken();
    }
}
