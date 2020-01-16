package com.wyk.dsmanager.repository;

import com.wyk.dsmanager.entity.DsDictionary;
import com.wyk.dsmanager.entity.vo.DsDictionaryVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository
public interface DictionaryRep extends JpaRepository<DsDictionary, String>, JpaSpecificationExecutor<DsDictionary> {
    @Query(value = "select * from ds_dictionary where type_id = ?", nativeQuery = true)
    List<DsDictionary> listByTypeId(String typeId);
}
