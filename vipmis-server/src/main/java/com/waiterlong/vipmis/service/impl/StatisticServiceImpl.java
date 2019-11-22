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
import java.util.*;

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
        //返回的只是数据库里存在的日期当天统计，因此需要从入参日期start到日期end一天一天的遍历进行统计
        List<Map<String, Object>> mapList = userRep.getUserGrowth(start, end);

        //返回的只是数据库里存在的日期当天统计，因此需要从入参日期start到日期end一天一天的遍历进行统计
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

    @Override
    public Result getConsumeLimitGrowth(String start, String end) {
        if (!isDate(start) || !isDate(end)) {
            return Result.badArgumentValue();
        }

        Date startDate = DateUtil.stringToDate(start, DateUtil.FMT_YYYY_MM_DD);
        Date endDate = DateUtil.stringToDate(end, DateUtil.FMT_YYYY_MM_DD);
        List<String> dateList = DateUtil.getRangeEveryDay(startDate, endDate);

        start += " 00:00:00";
        end += " 23:59:59";

        List<Map<String,Object>> totalList = depositLogRep.getConsumeLimitDayGroup(Constant.CHARGE, start, end);
        List<Object> consumeTotalList = new ArrayList<>();
        if(dateList != null & dateList.size() > 0){
            for (String dateStr : dateList) {
                boolean hasValue = false;
                for (Map<String,Object> map : totalList) {
                    String time = (String)map.get("time");
                    if(dateStr.equals(time)){
                        hasValue = true;
                        consumeTotalList.add(map.get("consume"));break;
                    }
                }
                if(!hasValue){
                    consumeTotalList.add(0);
                }
            }
        }

        Map<String, List<Object>> titleConsumeMap = new HashMap<>();
        List<Map<String,Object>> titleConsumeList = depositLogRep.getConsumeLimitTitleAndDayGroup(Constant.CHARGE, start, end);
        List<String> titleList = new ArrayList<>();
        for (Map<String, Object> map : titleConsumeList){
            String title = (String) map.get("title");
            if(!titleList.contains(title)){
                titleList.add(title);
            }
        }
        for (String title : titleList){
            List<Object> tempList = new ArrayList<>();
            for (String dateStr : dateList) {
                boolean hasValue = false;
                for (Map<String, Object> map : titleConsumeList) {
                    String curTitle = (String) map.get("title");
                    String curTime = (String) map.get("time");
                    if(title.equals(curTitle) && dateStr.equals(curTime)){
                        hasValue = true;
                        tempList.add(map.get("consume"));break;
                    }
                }
                if(!hasValue){
                    tempList.add(0);
                }
            }
            titleConsumeMap.put(title, tempList);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("titleList", titleList);
        result.put("dateList", dateList);
        result.put("lineResult", consumeTotalList);
        result.put("barResult", titleConsumeMap);
        return Result.ok(result);
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
