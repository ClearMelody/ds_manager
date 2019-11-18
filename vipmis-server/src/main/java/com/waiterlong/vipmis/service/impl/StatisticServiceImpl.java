package com.waiterlong.vipmis.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.repository.DepositLogRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.IStatisticService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Resource(name = "userRep")
    private UserRep userRep;

    @Override
    public Result getProductProportion(@NotNull String start, @NotNull String end) {
        if (!isDate(start) || !isDate(end)) {
            return Result.badArgumentValue();
        }
        start += " 00:00:00";
        end += " 23:59:59";
        return Result.ok(depositLogRep.getProductProportion(Constant.CHARGE, start, end));
    }

    @Override
    public Result getUserGrowth(@NotNull String start, @NotNull String end) {
        if (!isDate(start) || !isDate(end)) {
            return Result.badArgumentValue();
        }
        Date startDate = DateUtil.stringToDate(start, DateUtil.FMT_YYYY_MM_DD);
        Date endDate = DateUtil.stringToDate(end, DateUtil.FMT_YYYY_MM_DD);

        start += " 00:00:00";
        end += " 23:59:59";
        List<Map<String ,Object>> dest = Lists.newLinkedList();
        Long count = userRep.countByRegisterTimeLessThan(DateUtil.stringToDate(start));
        List<Map<String, Object>> mapList = userRep.getUserGrowth(start, end);

        for (Date date = startDate; date.before(endDate) || date.equals(endDate);) {
            boolean isHasData = false;
            for (Map<String, Object> map : mapList) {
                if (DateUtil.dateToString(date, DateUtil.FMT_YYYY_MM_DD).equals(map.get("time"))) {
                    count += ((BigInteger)map.get("sum")).longValue();
                    long tSum = count;
                    Map<String, Object> tMap = Maps.newLinkedHashMap();
                    tMap.put("name", map.get("time"));
                    tMap.put("sum", tSum);
                    dest.add(tMap);
                    isHasData = true;
                    break;
                }
            }
            if (!isHasData) {
                long tSum = count;
                Map<String, Object> tMap = Maps.newLinkedHashMap();
                tMap.put("name", DateUtil.dateToString(date, DateUtil.FMT_YYYY_MM_DD));
                tMap.put("sum", tSum);
                dest.add(tMap);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
        }

        return Result.ok(dest);
    }

    private Boolean isDate(String date) {
        if (null == date || date.trim().isEmpty()) {
            return false;
        }
        try {
            DateUtil.stringToDate(date, DateUtil.FMT_YYYY_MM_DD);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
