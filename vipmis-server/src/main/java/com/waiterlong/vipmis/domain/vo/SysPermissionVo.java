package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
public class SysPermissionVo {
    private String id;
    private String pid;
    private String name;
    private String href;
    private String permission;
    private String path;
    private Integer type;
    private Integer sort;
    private String pname;
    private List<SysPermissionVo> children;

    public static SysPermissionVo convertSysPermission(SysPermission permission) {
        if (null == permission) {
            return null;
        }
        SysPermissionVo permissionVo = new SysPermissionVo();
        AbstractMyBeanUtils.copyProperties(permission, permissionVo);
        return permissionVo;
    }
    public static List<SysPermissionVo> convertSysPermission(List<SysPermission> permissions){
        List<SysPermissionVo> permissionVos = Lists.newArrayList();
        for (SysPermission permission : permissions) {
            permissionVos.add(convertSysPermission(permission));
        }
        return permissionVos;
    }
}
