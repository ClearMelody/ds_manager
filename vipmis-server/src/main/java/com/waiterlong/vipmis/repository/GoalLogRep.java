package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.GoalLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Repository("goalLogRep")
public interface GoalLogRep extends JpaRepository<GoalLog, String> {
    /**
     * 分页列出积分记录
     *
     * @param pageable {Pageable}
     * @return Page<User>
     */
    Page<GoalLog> findByUser_IdOrderByCreateTimeDesc(String userId, Pageable pageable);
}
