package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    SysRole findByName(String name);
    /**
     * 分页查询角色信息
     * @param name
     * @param pageable
     * @return
     */
    Page<SysRole> findByNameIsContainingOrderByCreateTimeDesc(String name, Pageable pageable);
}
