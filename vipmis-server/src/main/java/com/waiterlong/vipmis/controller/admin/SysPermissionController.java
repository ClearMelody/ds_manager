package com.waiterlong.vipmis.controller.admin;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.vo.SysPermissionVo;
import com.waiterlong.vipmis.service.ISysPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/admin/permission")
public class SysPermissionController {
    @Resource(name = "iSysPermissionService")
    private ISysPermissionService sysPermissionService;

    @PostMapping("/allPermissions")
    public Result queryAllPermissions(@RequestBody SysPermission sysPermission){
        return sysPermissionService.queryAllPermissions(sysPermission);
    }

    @GetMapping("/getSysPermissionById/{id}")
    public Result getSysPermissionById(@PathVariable(value = "id") String id) { return sysPermissionService.queryOneEntity(id); };

    @PutMapping("/saveSysPermission")
    public Result saveSysPermission(@RequestBody SysPermissionVo sysPermissionVo){ return sysPermissionService.saveOrUpdate(sysPermissionVo); }

    @DeleteMapping("/deleteSysPermission/{id}")
    public Result deleteSysPermission(@PathVariable(value = "id") String id){ return sysPermissionService.deleteAllByOneId(id); };
}
