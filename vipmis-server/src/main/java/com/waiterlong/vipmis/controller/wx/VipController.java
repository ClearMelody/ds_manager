package com.waiterlong.vipmis.controller.wx;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.domain.wxvo.VipVo;
import com.waiterlong.vipmis.service.IUserService;
import com.waiterlong.vipmis.service.IVipService;
import com.waiterlong.vipmis.service.IWelcomeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/wx")
public class VipController {
    @Resource(name = "iVipService")
    private IVipService iVipService;
    @Resource(name = "iWelcomeService")
    private IWelcomeService iWelcomeService;

    @RequestMapping(value = "/vip/find", method = RequestMethod.GET)
    public Object findVipByOpenId(@RequestParam(defaultValue = "") String openid) {
        return iVipService.findVipByOpenId(openid);
    }

    @RequestMapping(value = "/vip/update", method = RequestMethod.POST)
    public Object findVipByOpenId(@RequestBody VipVo vipVo) {
        return iVipService.updateVipInfo(vipVo);
    }

    @RequestMapping(value = "/vip/balance/list", method = RequestMethod.POST)
    public Object listDepositLogByPage(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return iVipService.listDepositLogByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/vip/integral/list", method = RequestMethod.POST)
    public Object listGoalLogByPage(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return iVipService.listGoalLogByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Object getLastWelcome(){
        return iWelcomeService.getLastWelcome();
    }
}
