package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.vo.SysPermissionVo;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/2
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface ISysPermissionService {
    /**
     * 查询所有一级权限菜单
     * @return
     */
    Result queryAllPermissions(SysPermission sysPermission);
    Result queryOneEntity(String id);
    Result saveOrUpdate(@NotNull SysPermissionVo sysPermissionVo);
    Result deleteAllByOneId(String id);
}
