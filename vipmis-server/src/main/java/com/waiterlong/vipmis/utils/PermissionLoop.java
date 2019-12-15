package com.waiterlong.vipmis.utils;

import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.vo.SysPermissionVo;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/15
 * \*
 * \* Description
 *
 * @author wyk
 */
public class PermissionLoop {
    /**
     * 遍历获取当前list中没有父节点的对象
     * @param permissions
     * @return
     */
    public static List<SysPermissionVo> getNonePidPermissionList(List<SysPermission> permissions){
        List<SysPermissionVo> children = new ArrayList<>();
        for (SysPermission permission : permissions) {
            boolean pExist = false;
            for(SysPermission temp : permissions){
                if(temp.getId().equals(permission.getPid())){
                    pExist = true;break;
                }
            }
            if(!pExist){
                SysPermissionVo sysPermissionVo= SysPermissionVo.convertSysPermission(permission);
                children.add(sysPermissionVo);
            }
        }
        return children;
    }

    /**
     * 根据pid获取children
     * @param id
     * @param permissions
     * @return
     */
    public static List<SysPermissionVo> getPermissionListByPid(String id, List<SysPermission> permissions){
        List<SysPermissionVo> children = new ArrayList<>();
        for (SysPermission permission : permissions) {
            boolean checkParam = (id == null && null == permission.getPid()) || (id != null && id.equals(permission.getPid()));
            if(checkParam){
                SysPermissionVo temp= SysPermissionVo.convertSysPermission(permission);
                children.add(temp);
            }
        }
        return children;
    }

    /**
     * 递归遍历所有节点
     * @param permissionVos
     * @param permissions
     * @return
     */
    public static List<SysPermissionVo> loopPermissions(List<SysPermissionVo> permissionVos, List<SysPermission> permissions) {

        for (SysPermissionVo permissionVo : permissionVos) {
            permissionVo.setChildren(getPermissionListByPid(permissionVo.getId(), permissions));
            if(permissionVo.getChildren().isEmpty()) {
                continue;
            }
            loopPermissions(permissionVo.getChildren(), permissions);
        }
        return permissionVos;
    }
}
