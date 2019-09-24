package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.service.base.IBaseService;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
public interface IGoalService extends IBaseService {
    /**
     * 分页列出积分记录
     * @param paramMap {Map}
     * @param pageable {Pageable}
     * @return Object
     */
    Object listGoalLogByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 添加用户
     * @param goalLogVo {GoalLogVo}
     * @return Object
     */
    Object addGoalLog(GoalLogVo goalLogVo);
}
