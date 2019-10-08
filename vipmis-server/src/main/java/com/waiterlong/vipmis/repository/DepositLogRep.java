package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.DepositLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Repository("depositLogRep")
public interface DepositLogRep extends JpaRepository<DepositLog, String> {
    /**
     * 分页查余额记录
     * @param userId String
     * @param pageable Pageable
     * @return Page<DepositLog>
     */
    Page<DepositLog> findByUser_IdOrderByCreateTimeDesc(String userId, Pageable pageable);
}
