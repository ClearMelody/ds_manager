package com.wyk.dsmanager.repository;

import com.wyk.dsmanager.entity.DsDataResource;
import com.wyk.dsmanager.entity.vo.DsDataResourceVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/01/02
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository
public interface DataResourceRep extends JpaRepository<DsDataResource, String> {
    /**
     * 查询所有
     * @return
     */
    @Query("select new com.wyk.dsmanager.entity.vo.DsDataResourceVo(r.id,r.dsName) from DsDataResource r")
    List<DsDataResourceVo> listAll();

    /**
     * 根据名称查找
     * @param dsName
     * @return
     */
    DsDataResource findDsDataResourceByDsName(String dsName);
}
