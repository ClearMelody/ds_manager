package com.wyk.dsmanager.repository;

import com.wyk.dsmanager.entity.DsDictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
public interface DictionaryTypeRep extends JpaRepository<DsDictionaryType, String> {
    List<DsDictionaryType> findByOrderBySortAscCreateTimeAsc();
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(nativeQuery = true, value = "delete from ds_dictionary_type where find_in_set(?, path)")
    void deleteAllByOneId(String id);
}
