package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.service.IVipService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @RequiresPermissions("user:list")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public Result listDepositLogByPage(
            @RequestParam(defaultValue = "") String realName,
            @RequestParam(defaultValue = "") String weChatName,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String idCard,
            @RequestParam(defaultValue = "") String cardCord,
            @RequestParam(defaultValue = "") String labelId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("realName", realName);
        paramMap.put("weChatName", weChatName);
        paramMap.put("phone", phone);
        paramMap.put("idCard", idCard);
        paramMap.put("cardCord", cardCord);
        paramMap.put("labelId", labelId);
        return iVipService.listVipsByPage(paramMap, pageable);
    }

    //@RequiresPermissions("user:charge")
    @RequestMapping(value = "/user/charge", method = RequestMethod.POST)
    public Result charge(@RequestBody UserInfoVo userInfoVo) {
        return iVipService.charge(userInfoVo);
    }

    //@RequiresPermissions("user:pay")
    @RequestMapping(value = "/user/pay", method = RequestMethod.POST)
    public Result pay(@RequestBody UserInfoVo userInfoVo) {
        return iVipService.pay(userInfoVo);
    }

    //@RequiresPermissions("user:goal:use")
    @RequestMapping(value = "/user/goal/use", method = RequestMethod.POST)
    public Result useGoal(@RequestBody UserInfoVo userInfoVo) {
        return iVipService.useGoal(userInfoVo);
    }
}
