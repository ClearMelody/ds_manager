package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.SysRoleVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.repository.SysPermissionRep;
import com.waiterlong.vipmis.repository.SysRoleRep;
import com.waiterlong.vipmis.service.ISysRoleService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/1
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("iSysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl implements ISysRoleService {
    private Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);
    @Resource(name = "sysRoleRep")
    private SysRoleRep sysRoleRep;
    @Resource(name = "sysPermissionRep")
    private SysPermissionRep sysPermissionRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveSysRole(@NotNull SysRoleVo sysRoleVo) {
        SysRole role = null;
        String permissionIds = sysRoleVo.getPermissionIds();
        if (StringUtils.isNotBlank(sysRoleVo.getId())) {
            role = sysRoleRep.getOne(sysRoleVo.getId());
            AbstractMyBeanUtils.copyProperties(sysRoleVo, role);
            logger.debug("更新角色{}", role.getName());
        } else {// 新增
            role = new SysRole();
            SysRole r = sysRoleRep.findByName(role.getName());
            AbstractMyBeanUtils.copyProperties(sysRoleVo, role);
            if (r != null) {
                return Result.fail(Result.ERROR_CODE, "保存失败，角色" + role.getName() + "已存在！");
            }
            role.setCreateTime(new Date());
            logger.debug("新增角色{}", role.getName());
        }
        role.setUpdateTime(new Date());
        role.setSysPermissions(getSysPermissions(permissionIds));
        role = sysRoleRep.save(role);
        return Result.ok();
    }

    /**
     * 根据权限id集合查询权限实体集合
     * @param permissionIds
     * @return
     */
    private List<SysPermission> getSysPermissions(String permissionIds){
        if(StringUtils.isNotBlank(permissionIds)){
            List<String> idList = Arrays.asList(permissionIds.split(","));
            return sysPermissionRep.querySysPermissionsByIds(idList);
        }
        return null;
    }

    @Override
    public Result listSysRolesByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<SysRole> rolePage = sysRoleRep.findByNameIsContainingOrderByCreateTimeDesc((String)paramMap.get("name"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, rolePage.getTotalElements(), SysRoleVo.convertSysRole(rolePage.getContent())));
    }

    @Override
    public Result getSysRoleById(String id) {
        if(StringUtils.isBlank(id)){
            return Result.badArgumentValue();
        }
        SysRole sysRole = sysRoleRep.getOne(id);
        SysRoleVo sysRoleVo = SysRoleVo.convertSysRole(sysRole);
        return Result.ok(sysRoleVo);
    }

    @Override
    public Result deleteSysRoleById(String id) {
        if(StringUtils.isBlank(id)){
            return Result.badArgumentValue();
        }
        sysRoleRep.deleteById(id);
        return Result.ok();
    }

    @Override
    public Result listAllBaseRoleInfos() {
        return Result.ok(sysRoleRep.findAllBaseRoleInfos());
    }

}
