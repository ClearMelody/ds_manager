package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysUser;
import com.waiterlong.vipmis.domain.vo.SysRoleVo;
import com.waiterlong.vipmis.domain.vo.SysUserVo;
import com.waiterlong.vipmis.repository.SysRoleRep;
import com.waiterlong.vipmis.repository.SysUserRep;
import com.waiterlong.vipmis.service.ISysUserService;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/9
 * \*
 * \* Description
 *
 * @author wyk
 */
@Slf4j
@Service("iSysUserService")
public class SysUserServiceImpl implements ISysUserService {
    @Resource(name = "sysUserRep")
    SysUserRep sysUserRep;
    @Resource(name = "sysRoleRep")
    SysRoleRep sysRoleRep;

    @Override
    public Result findSysUserInfoById(String id) {
        if(StringUtils.isBlank(id)){
            return Result.badArgumentValue();
        }else{
            SysUser sysUser = sysUserRep.getOne(id);
            return Result.ok(SysUserVo.convertSysUser(sysUser));
        }
    }

    @Override
    public Result addSysUser(SysUserVo sysUserVo) {
        String id = sysUserVo.getId();
        SysUser sysUser = null;
        if(StringUtils.isBlank(sysUserVo.getName()) || StringUtils.isBlank(sysUserVo.getPassword()) || StringUtils.isBlank(sysUserVo.getRoleId())){
            return Result.fail(Result.ERROR_CODE, "保存失败，请填写完整信息！");
        }
        if(StringUtils.isNotBlank(sysUserVo.getPassword())){
            sysUserVo.setPassword(Md5Utils.encryptToMD5(sysUserVo.getName(), sysUserVo.getPassword()));
        }
        if(StringUtils.isNotBlank(id)){
            sysUser = sysUserRep.getOne(id);
            AbstractMyBeanUtils.copyProperties(sysUserVo, sysUser);
            log.debug("更新用户{}信息", sysUser.getName());
        }else{
            sysUser = new SysUser();
            SysUser u = sysUserRep.findByName(sysUserVo.getName());
            if (u != null) {
                return Result.fail(Result.ERROR_CODE, "保存失败，用户" + sysUser.getName() + "已存在！");
            }
            AbstractMyBeanUtils.copyProperties(sysUserVo, sysUser);
            sysUser.setCreateTime(new Date());
            log.debug("新增用户{}信息", sysUser.getName());
        }
        sysUser.setSysRole(sysRoleRep.getOne(sysUserVo.getRoleId()));
        sysUserRep.save(sysUser);
        return Result.ok();
    }

    @Override
    public Result listSysUsersByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<SysUserVo> page = sysUserRep.findSysUserByNameContainsOrderByCreateTimeDesc((String) paramMap.get("name"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, page.getTotalElements(), page.getContent()));
    }
}