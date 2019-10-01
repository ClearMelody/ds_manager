package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

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
public class SysRoleVo {
    private String id;

    public static SysRoleVo convertSysRole(SysRole sysRole) {
        if (null == sysRole) {
            return null;
        }
        SysRoleVo sysRoleVo = new SysRoleVo();
        AbstractMyBeanUtils.copyProperties(sysRole, sysRoleVo);
        return sysRoleVo;
    }
    public static List<SysRoleVo> convertSysRole(List<SysRole> sysRoles){
        List<SysRoleVo> sysRoleVos = Lists.newArrayList();
        for (SysRole sysRole : sysRoles) {
            sysRoleVos.add(convertSysRole(sysRole));
        }
        return sysRoleVos;
    }
}
