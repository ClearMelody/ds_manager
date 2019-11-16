package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.repository.DepositLogRep;
import com.waiterlong.vipmis.service.IStatisticService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/16
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Service("iStatisticService")
public class StatisticServiceImpl extends BaseServiceImpl implements IStatisticService  {
    Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

    @Resource(name = "depositLogRep")
    private DepositLogRep depositLogRep;

    @Override
    public Result getProductProportion(@NotNull Date start, @NotNull Date end) {
        return Result.ok(depositLogRep.getProductProportion(start, end));
    }
}
