package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.DepositLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    List<DepositLog> findByUser_Id(String userId);

    @Query(nativeQuery = true, value = "select title, sum(value) as sum from deposit_log where create_time between ? and ? GROUP BY title")
    List<Map<String, Object>> getProductProportion(Date start, Date end);
}
