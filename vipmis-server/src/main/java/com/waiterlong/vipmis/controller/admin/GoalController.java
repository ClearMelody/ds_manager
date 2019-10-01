package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.service.IGoalService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RestController
@RequestMapping("/api/admin/goal")
public class GoalController {
    @Resource(name = "iGoalService")
    private IGoalService goalService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object listGoalLogByPage(
            @RequestParam(defaultValue = "") String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return goalService.listGoalLogByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addGoalLog(@RequestBody GoalLogVo goalLogVo){
        return  goalService.addGoalLog(goalLogVo);
    }
}
