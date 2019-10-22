package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.Welcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/22
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Repository("welcomeRep")
public interface WelcomeRep extends JpaRepository<Welcome, String> {
    Welcome findTopByOrderByCreateTimeDesc();
    Welcome findTopByOrderByCreateTimeAsc();
//    Integer countBy();
}
