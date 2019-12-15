package com.waiterlong.vipmis.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.ILoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/14
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/admin/")
public class LoginController {
    @Resource(name = "iLoginService")
    ILoginService loginService;

    /**
     * 登录
     * @param params
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Object> params) {
        return loginService.authLogin(params);
    }

    /**
     * 查询当前登录用户的信息
     */
    @PostMapping("/getUserInfo")
    public Result getUserInfo() {
        return loginService.getUserInfo();
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }
}
