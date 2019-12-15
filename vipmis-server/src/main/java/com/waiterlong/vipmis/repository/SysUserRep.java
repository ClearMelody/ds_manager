package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.SysRole;
import com.waiterlong.vipmis.domain.SysUser;
import com.waiterlong.vipmis.domain.vo.SysUserVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/9
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository("sysUserRep")
public interface SysUserRep extends JpaRepository<SysUser,String> {
    /**
     * 查询用户名是否已存在
     * @param name
     * @return
     */
    SysUser findByName(String name);

    /**
     * 根据用户名和密码查询用户信息
     * @param name
     * @param password
     * @return
     */
    SysUser findByNameAndPassword(String name, String password);
    /**
     * 分页查询角色信息
     * @param nameLike
     * @param pageable
     * @return
     */
    @Query("select new com.waiterlong.vipmis.domain.vo.SysUserVo(u.id, u.name, u.createTime, r.name as roleName) from SysUser u left join u.sysRole r where u.name like %:nameLike%")
    Page<SysUserVo> findSysUserByNameContainsOrderByCreateTimeDesc(@Param("nameLike")String nameLike, Pageable pageable);
}
