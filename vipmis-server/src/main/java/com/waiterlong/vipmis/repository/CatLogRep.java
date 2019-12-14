package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.CatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/14
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Repository("catLogRep")
public interface CatLogRep extends JpaRepository<CatLog, String> {
    Page<CatLog> findByCat_IdIsOrderByCreateTimeDesc(String catId, Pageable pageable);
}