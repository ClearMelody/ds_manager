package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.domain.vo.SysRoleVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/1
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository("sysRoleRep")
public interface SysRoleRep extends JpaRepository<SysRole,String> {
    /**
     * 根据角色名查询角色信息
     * @param name
     * @return
     */
    SysRole findByName(String name);
    /**
     * 分页查询角色信息
     * @param name
     * @param pageable
     * @return
     */
    Page<SysRole> findByNameIsContainingOrderByCreateTimeDesc(String name, Pageable pageable);

    /**
     * 查询所有角色基本信息
     * @return
     */
    @Query("select new com.waiterlong.vipmis.domain.vo.SysRoleVo(r.id, r.name) from SysRole r")
    List<SysRoleVo> findAllBaseRoleInfos();
}
