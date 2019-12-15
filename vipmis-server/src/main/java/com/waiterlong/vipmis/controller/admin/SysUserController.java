package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.controller.base.BaseController;
import com.waiterlong.vipmis.domain.vo.SysUserVo;
import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.service.ISysUserService;
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
    @Resource(name = "iSysUserService")
    private ISysUserService iSysUserService;

    @GetMapping(value = "/find/{id}")
    public Result findSysUserInfoById(@PathVariable(value = "id") String id){
        return  iSysUserService.findSysUserInfoById(id);
    }

    @PutMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUserVo sysUserVo){
        return iSysUserService.addSysUser(sysUserVo);
    }

    @PostMapping(value = "/list")
    public Result listByPage(@RequestBody Map<String,Object> params,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        return iSysUserService.listSysUsersByPage(params, pageable);
    }
}
