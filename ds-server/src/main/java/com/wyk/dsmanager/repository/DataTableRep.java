package com.wyk.dsmanager.repository;

import com.wyk.dsmanager.entity.DsDataTable;
import com.wyk.dsmanager.entity.vo.DsDataTableVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository
public interface DataTableRep extends JpaRepository<DsDataTable, String> {
    @Query("select new com.wyk.dsmanager.entity.vo.DsDataTableVo(dt.id,dt.dtNameCn) from DsDataTable dt left join dt.dsDataResource dr where dr.id = :resId")
    List<DsDataTableVo> findByDsDataResource_IdOrderByCreateTimeDesc(@Param("resId") String resId);
}
