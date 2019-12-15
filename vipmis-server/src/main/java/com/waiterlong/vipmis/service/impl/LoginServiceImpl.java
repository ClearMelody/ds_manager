package com.waiterlong.vipmis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysUser;
import com.waiterlong.vipmis.domain.vo.SysPermissionVo;
import com.waiterlong.vipmis.domain.vo.SysUserVo;
import com.waiterlong.vipmis.repository.SysUserRep;
import com.waiterlong.vipmis.service.ILoginService;
import com.waiterlong.vipmis.utils.Constants;
import com.waiterlong.vipmis.utils.PermissionLoop;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/14
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("iLoginService")
public class LoginServiceImpl implements ILoginService {
    @Resource(name = "sysUserRep")
    SysUserRep sysUserRep;

    @Override
    public SysUserVo getUser(String name) {
        SysUser user = sysUserRep.findByName(name);
        return SysUserVo.convertSysUser(user);
    }

    @Override
    public Result authLogin(Map<String, Object> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            info.put("result", "success");
        } catch (AuthenticationException e) {
            info.put("result", e.getMessage());
        }
        return Result.ok(info);
    }

    @Override
    public Result getUserInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysUserVo sysUserVo = (SysUserVo) session.getAttribute("userInfo");
        String userId = sysUserVo.getId();
        SysUser user = sysUserRep.getOne(userId);
        List<SysPermission> sysPermissions = user.getSysRole().getSysPermissions();
        Set<String> permissions = filterPermissions(sysPermissions);
        List<SysPermissionVo> menus = getMenuList(sysPermissions);
        sysUserVo.setPermissions(permissions);
        sysUserVo.setMenus(menus);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, permissions);
        return Result.ok(sysUserVo);
    }

    private Set<String> filterPermissions(List<SysPermission> permissionList){
        Set<String> permissions = new HashSet<>();
        for (SysPermission permission : permissionList){
            if(StringUtils.isNotBlank(permission.getPermission())){
                permissions.add(permission.getPermission());
            }
        }
        return permissions;
    }

    private List<SysPermissionVo> getMenuList(List<SysPermission> permissionList){
        List<SysPermission> list = new ArrayList<>();
        for (SysPermission permission : permissionList){
            if(permission.getType() == 1){
                list.add(permission);
            }
        }
        list = list.stream().sorted(Comparator.comparing(SysPermission::getSort)).collect(Collectors.toList());
        List<SysPermissionVo> permissionVos = PermissionLoop.getNonePidPermissionList(list);
        permissionVos = PermissionLoop.loopPermissions(permissionVos, list);
        return permissionVos;
    }

    @Override
    public Result logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return Result.ok();
    }
}
