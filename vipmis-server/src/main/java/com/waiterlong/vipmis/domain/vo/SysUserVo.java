package com.waiterlong.vipmis.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysUser;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/2
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
@NoArgsConstructor
public class SysUserVo {
    private String id;

    private String name;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String roleName;

    private String roleId;

    private SysRoleVo sysRoleVo;

    private Set<String> permissions;

    private List<SysPermissionVo> menus;

    public SysUserVo(String id, String name, Date createTime, String roleName) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.roleName = roleName;
    }

    public static SysUserVo convertSysUser(SysUser sysUser) {
        if (null == sysUser) {
            return null;
        }
        SysUserVo sysUserInfoVo = new SysUserVo();
        AbstractMyBeanUtils.copyProperties(sysUser, sysUserInfoVo);
        sysUserInfoVo.setSysRoleVo(SysRoleVo.convertSysRole(sysUser.getSysRole()));
        return sysUserInfoVo;
    }
    public static List<SysUserVo> convertSysUser(List<SysUser> sysUsers){
        List<SysUserVo> sysUserInfoVos = Lists.newArrayList();
        for (SysUser sysUser : sysUsers) {
            sysUserInfoVos.add(convertSysUser(sysUser));
        }
        return sysUserInfoVos;
    }
}
