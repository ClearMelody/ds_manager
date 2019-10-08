package com.waiterlong.vipmis.controller.wx;

import com.waiterlong.vipmis.service.IUserService;
import com.waiterlong.vipmis.service.IVipService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @RequestMapping(value = "/vip/find", method = RequestMethod.GET)
    public Object findVipByOpenId(@RequestParam(defaultValue = "") String openid){
        return  iVipService.findVipByOpenId(openid);
    }
}
