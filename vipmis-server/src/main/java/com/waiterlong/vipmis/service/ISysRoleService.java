package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.domain.vo.SysRoleVo;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/1
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface ISysRoleService {
    /**
     * 保存或更新角色信息
     * @param sysRoleVo
     * @return
     */
    Result saveSysRole(SysRoleVo sysRoleVo);

    /**
     * 分页查询角色信息
     * @param paramMap
     * @param pageable
     * @return
     */
    Result listSysRolesByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    Result getSysRoleById(String id);

    /**
     * 根据id删除角色信息
     * @param id
     * @return
     */
    Result deleteSysRoleById(String id);
}
