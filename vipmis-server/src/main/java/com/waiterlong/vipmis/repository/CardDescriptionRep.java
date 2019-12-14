package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.CardDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/15
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Repository("cardDescriptionRep")
public interface CardDescriptionRep extends JpaRepository<CardDescription, String> {
    CardDescription findTopByOrderByCreateTimeDesc();
    CardDescription findTopByOrderByCreateTimeAsc();
}
