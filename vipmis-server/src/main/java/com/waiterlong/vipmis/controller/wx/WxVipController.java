package com.waiterlong.vipmis.controller.wx;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.wxvo.VipVo;
import com.waiterlong.vipmis.service.*;
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
public class WxVipController {
    @Resource(name = "iVipService")
    private IVipService iVipService;
    @Resource(name = "iWelcomeService")
    private IWelcomeService iWelcomeService;
    @Resource(name = "iWeiXinApiService")
    private IWeiXinApiService iWeiXinApiService;
    @Resource(name = "iCatService")
    private ICatService iCatService;

    @RequestMapping(value = "/vip/find", method = RequestMethod.GET)
    public Result findVipByOpenId(@RequestParam(defaultValue = "") String openid) {
        return iVipService.findVipByOpenId(openid);
    }

    @RequestMapping(value = "/vip/update", method = RequestMethod.POST)
    public Result updateVipInfo(@RequestBody VipVo vipVo) {
        return iVipService.updateVipInfo(vipVo);
    }

    @RequestMapping(value = "/vip/add", method = RequestMethod.POST)
    public Result addVipInfo(@RequestBody VipVo vipVo) {
        return iVipService.addVipInfo(vipVo);
    }

    @RequestMapping(value = "/vip/balance/list", method = RequestMethod.GET)
    public Result listDepositLogByPage(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return iVipService.listDepositLogByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/vip/integral/list", method = RequestMethod.GET)
    public Result listGoalLogByPage(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return iVipService.listGoalLogByPage(paramMap, pageable);
    }

//    @RequestMapping(value = "/vip/delete", method = RequestMethod.GET)
    public Result listGoalLogByPage(
            @RequestParam String userId) {
        return iVipService.deleteUser(userId);
    }

    @RequestMapping(value = "/cat/detail", method = RequestMethod.GET)
    public Result getCatDetail(
            @RequestParam String catId) {
        return iCatService.getCatDetail(catId);
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Result getLastWelcome(){
        return iWelcomeService.getLastWelcome();
    }

    /**
     * 微信小程序获取openid
     * @param code String
     * @param secretCode String
     * @param appid String
     * @return Result
     */
    @RequestMapping(value = "/wxapi/openid", method = RequestMethod.GET)
    public Result getOpenid(
            @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String secretCode,
            @RequestParam(defaultValue = "") String appid){
        return iWeiXinApiService.getOpenid(code, secretCode, appid);
    }
}
