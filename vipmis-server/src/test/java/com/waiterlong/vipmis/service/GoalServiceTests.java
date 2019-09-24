package com.waiterlong.vipmis.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.assets.WeiXinApiUrl;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoalServiceTests {
    @Resource(name = "iGoalService")
    private IGoalService goalService;

    @Resource(name = "iUserService")
    private IUserService userService;

    @Test
    public void listGoalLogByPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", Constant.TEST_USER_ID);

        Map result = (Map)goalService.listGoalLogByPage(paramMap, pageable);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void addGoalLog() {
        Map resultt = (Map)userService.findUserInfoById(Constant.TEST_USER_ID);
        UserInfoVo userInfoVo = (UserInfoVo)resultt.get(WeiXinApiUrl.DATA_KEY);
        GoalLogVo goalLogVo = new GoalLogVo();
        goalLogVo.setUserInfoVo(userInfoVo);
        goalLogVo.setGoalChange(4L);
        Map result = (Map)goalService.addGoalLog(goalLogVo);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }
}
