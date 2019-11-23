package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.service.IVipService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/31
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Resource(name = "iVipService")
    private IVipService iVipService;

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public Result listDepositLogByPage(
            @RequestParam(defaultValue = "") String realName,
            @RequestParam(defaultValue = "") String weChatName,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String idCard,
            @RequestParam(defaultValue = "") String cardCord,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("realName", realName);
        paramMap.put("weChatName", weChatName);
        paramMap.put("phone", phone);
        paramMap.put("idCard", idCard);
        paramMap.put("cardCord", cardCord);
        return iVipService.listVipsByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/user/charge", method = RequestMethod.POST)
    public Result charge(@RequestBody UserInfoVo userInfoVo) {
        return iVipService.charge(userInfoVo);
    }

    @RequestMapping(value = "/user/pay", method = RequestMethod.POST)
    public Result pay(@RequestBody UserInfoVo userInfoVo) {
        return iVipService.pay(userInfoVo);
    }

    @RequestMapping(value = "/user/goal/use", method = RequestMethod.POST)
    public Result useGoal(@RequestBody UserInfoVo userInfoVo) {
        return iVipService.useGoal(userInfoVo);
    }
}
