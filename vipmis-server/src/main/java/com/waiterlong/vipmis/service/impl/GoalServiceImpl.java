package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.domain.GoalLog;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.repository.GoalLogRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.IGoalService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Service("iGoalService")
public class GoalServiceImpl extends BaseServiceImpl implements IGoalService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "goalLogRep")
    private GoalLogRep goalLogRep;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Override
    public Object listGoalLogByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<GoalLog> goalLogPage = goalLogRep.findByUser_IdOrderByCreateTimeDesc((String)paramMap.get("userId"), pageable);
        return ResponseUtil.ok(PageResult.setPageResult(pageable, goalLogPage.getTotalElements(), GoalLogVo.convertGoalLog(goalLogPage.getContent())));
    }

    @Override
    public Object addGoalLog(@NotNull GoalLogVo goalLogVo) {
        if (null == goalLogVo.getUserInfoVo() || null == goalLogVo.getUserInfoVo().getId()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(goalLogVo.getUserInfoVo().getId());
        if (!userOptional.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        User user = userOptional.get();
        Long goal = user.getGoal();
        GoalLog goalLog = new GoalLog();
        AbstractMyBeanUtils.copyProperties(goalLogVo, goalLog);
        if (null == goalLog.getGoalChange()) {
            goalLog.setGoalChange(0L);
        } else {
            goal += goalLog.getGoalChange();
            user.setGoal(goal);
            user = userRep.saveAndFlush(user);
        }
        goalLog.setUser(user);
        goalLog = goalLogRep.save(goalLog);

        return ResponseUtil.ok(GoalLogVo.convertGoalLog(goalLog));
    }
}
