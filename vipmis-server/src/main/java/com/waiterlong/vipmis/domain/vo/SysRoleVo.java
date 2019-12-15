package com.waiterlong.vipmis.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;

import javax.persistence.Column;
import java.util.ArrayList;
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
@NoArgsConstructor
public class SysRoleVo {
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String permissionIds;

    public SysRoleVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SysRoleVo convertSysRole(SysRole sysRole) {
        if (null == sysRole) {
            return null;
        }
        SysRoleVo sysRoleVo = new SysRoleVo();
        AbstractMyBeanUtils.copyProperties(sysRole, sysRoleVo);
        List<SysPermission> list = sysRole.getSysPermissions();
        if(list != null){
            List<String> permissionIdList = new ArrayList<>(list.size());
            String permissionIdArr[] = new String[list.size()];
            for (int i = 0, len = list.size(); i < len; i++) {
                permissionIdArr[i] = list.get(i).getId();
            }
            sysRoleVo.setPermissionIds(StringUtils.join(permissionIdArr));
        }
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
