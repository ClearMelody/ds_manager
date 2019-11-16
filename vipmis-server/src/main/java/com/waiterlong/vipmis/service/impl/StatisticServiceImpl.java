package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.repository.DepositLogRep;
import com.waiterlong.vipmis.service.IStatisticService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.DateUtil;
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
    public Result getProductProportion(@NotNull String start, @NotNull String end) {
        if (start.trim().isEmpty() || end.trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        try {
            DateUtil.stringToDate(start, DateUtil.FMT_YYYY_MM_DD);
            DateUtil.stringToDate(end, DateUtil.FMT_YYYY_MM_DD);
        } catch (Exception e) {
            return Result.badArgumentValue();
        }
        start += " 00:00:00";
        end += " 23:59:59";
        return Result.ok(depositLogRep.getProductProportion(Constant.CHARGE, start, end));
    }
}
