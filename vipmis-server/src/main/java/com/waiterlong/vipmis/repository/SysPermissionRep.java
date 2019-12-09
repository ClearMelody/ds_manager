package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.SysPermission;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository("sysPermissionRep")
public interface SysPermissionRep extends JpaRepository<SysPermission,String>, JpaSpecificationExecutor<SysPermission> {

    /**
     * 根据权限菜单id删除该权限菜单及其所有子权限菜单
     * @param id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(nativeQuery = true, value = "delete from sys_permission where find_in_set(?, path)")
    void deleteAllByOneId(String id);

    /**
     * 根据权限id集合查询权限实体集合
     * @param ids
     * @return
     */
    @Query(nativeQuery = true, value = "select * from sys_permission where id in (:ids)")
    List<SysPermission> querySysPermissionsByIds(@Param("ids") List<String> ids);

}
