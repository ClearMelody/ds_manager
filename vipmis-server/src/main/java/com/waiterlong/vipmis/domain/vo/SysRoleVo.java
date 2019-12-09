package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.DateUtil;
import lombok.Data;
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
public class SysRoleVo {
    private String id;
    private String name;
    private Date createTime;
    private String createTimeStr;
    private Date updateTime;
    private String updateTimeStr;
    private String permissionIds;

    public static SysRoleVo convertSysRole(SysRole sysRole) {
        if (null == sysRole) {
            return null;
        }
        SysRoleVo sysRoleVo = new SysRoleVo();
        AbstractMyBeanUtils.copyProperties(sysRole, sysRoleVo);
        sysRoleVo.setCreateTimeStr(DateUtil.dateToString(sysRoleVo.getCreateTime()));
        sysRoleVo.setUpdateTimeStr(DateUtil.dateToString(sysRoleVo.getUpdateTime()));
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
