package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.GoalLog;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-15 08:51
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Data
public class GoalLogVo {
    private String id;

    private Long goalChange;

    private Date createTime;

    private UserInfoVo userInfoVo;

    public static GoalLogVo convertGoalLog(GoalLog goalLog) {
        if (null == goalLog) {
            return null;
        }
        GoalLogVo goalLogVo = new GoalLogVo();
        AbstractMyBeanUtils.copyProperties(goalLog, goalLogVo);
        goalLogVo.setUserInfoVo(UserInfoVo.convertUser(goalLog.getUser()));
        return goalLogVo;
    }
    public static List<GoalLogVo> convertGoalLog(List<GoalLog> goalLogs){
        List<GoalLogVo> goalLogVos = Lists.newArrayList();
        for (GoalLog goalLog : goalLogs) {
            goalLogVos.add(convertGoalLog(goalLog));
        }
        return goalLogVos;
    }
}
