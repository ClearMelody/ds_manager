package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.controller.base.BaseController;
import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.service.ICouponService;
import com.waiterlong.vipmis.service.IUserService;
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
@RequestMapping("/api/admin/sysuser")
public class SysUserController extends BaseController {
    @Resource(name = "iUserService")
    private IUserService userService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Object findUserInfoById(@RequestParam(defaultValue = "") String id){
        return  userService.findUserInfoById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUser(@RequestBody UserVo userVo){
        return  userService.addUser(userVo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object listTasksByPage(
                                  @RequestParam(defaultValue = "") String realName,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("realName", realName);
        return userService.listUsersByPage(paramMap, pageable);
    }
}
