package com.waiterlong.vipmis.domain.wxvo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.GoalLog;
import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.DateUtil;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class IntegralLogVo {
    private String title;

    private String time;

    private Long value;

    public static IntegralLogVo convertGoalLog(GoalLog goalLog) {
        if (null == goalLog) {
            return null;
        }
        IntegralLogVo integralLogVo = new IntegralLogVo();
        AbstractMyBeanUtils.copyProperties(goalLog, integralLogVo);
        integralLogVo.setTime(DateUtil.dateToString(goalLog.getCreateTime(), DateUtil.FMT_YYYY_MM_DD));
        integralLogVo.setValue(goalLog.getGoalChange());
        return integralLogVo;
    }
    public static List<IntegralLogVo> convertGoalLog(List<GoalLog> goalLogs){
        List<IntegralLogVo> integralLogVos = Lists.newArrayList();
        for (GoalLog goalLog : goalLogs) {
            integralLogVos.add(convertGoalLog(goalLog));
        }
        return integralLogVos;
    }
}
