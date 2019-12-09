package com.waiterlong.vipmis.controller.admin;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.SysRoleVo;
import com.waiterlong.vipmis.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/11/29
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/admin/sysrole")
public class SysRoleController {

    @Autowired
    private ISysRoleService iSysRoleService;

    @PutMapping("/saveRole")
    public Result saveRole(@RequestBody SysRoleVo sysRoleVo){
        return iSysRoleService.saveSysRole(sysRoleVo);
    }

    @PostMapping("/listSysRolesByPage")
    public Result listSysRolesByPage(@RequestBody Map<String,Object> params,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        return iSysRoleService.listSysRolesByPage(params, pageable);
    };

    @GetMapping("/getSysRole/{id}")
    public Result getSysRoleById(@PathVariable(value = "id") String id){return iSysRoleService.getSysRoleById(id);};

    @DeleteMapping("/deleteSysRole/{id}")
    public Result deleteSysRoleById(@PathVariable(value = "id") String id){return iSysRoleService.deleteSysRoleById(id);};
}
