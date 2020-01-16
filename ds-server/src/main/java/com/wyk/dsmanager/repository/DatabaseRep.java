package com.wyk.dsmanager.repository;

import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.entity.vo.DsDatabaseVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository
public interface DatabaseRep extends JpaRepository<DsDatabase, String> {
    /**
     * 分页查询数据源信息
     * @param dbName
     * @param dbUrl
     * @param dbType
     * @param pageable
     * @return
     */
    @Query("select new com.wyk.dsmanager.entity.vo.DsDatabaseVo(d.id, d.dbName, d.dbType, d.dbUrl, d.dbUsername, d.dbUsable, d.createTime) from DsDatabase d where d.dbName like %:dbName% and d.dbUrl like %:dbUrl% and d.dbType like %:dbType%")
    Page<DsDatabaseVo> findEntitiesForListPage(@Param("dbName")String dbName, @Param("dbUrl")String dbUrl, @Param("dbType")String dbType, Pageable pageable);
    /**
     * 查询所有数据源信息
     * @return
     */
    @Query("select new com.wyk.dsmanager.entity.vo.DsDatabaseVo(d.id, d.dbName) from DsDatabase d")
    List<DsDatabaseVo> listAll();

    /**
     * 根据名称查找
     * @param dbName
     * @return
     */
    DsDatabase findDsDatabaseByDbName(String dbName);
}
