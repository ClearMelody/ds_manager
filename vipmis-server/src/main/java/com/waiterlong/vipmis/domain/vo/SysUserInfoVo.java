package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.SysUser;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
public class SysUserInfoVo {
    private String id;

    private String name;

    private String password;

    private Date createTime;

    private SysRoleVo sysRoleVo;

    public static SysUserInfoVo convertSysUser(SysUser sysUser) {
        if (null == sysUser) {
            return null;
        }
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        AbstractMyBeanUtils.copyProperties(sysUser, sysUserInfoVo);
        sysUserInfoVo.setSysRoleVo(SysRoleVo.convertSysRole(sysUser.getSysRole()));
        return sysUserInfoVo;
    }
    public static List<SysUserInfoVo> convertSysUser(List<SysUser> sysUsers){
        List<SysUserInfoVo> sysUserInfoVos = Lists.newArrayList();
        for (SysUser sysUser : sysUsers) {
            sysUserInfoVos.add(convertSysUser(sysUser));
        }
        return sysUserInfoVos;
    }
}
