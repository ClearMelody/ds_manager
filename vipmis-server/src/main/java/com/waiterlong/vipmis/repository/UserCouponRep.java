package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.UserCoupon;
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
@Repository("userCouponRep")
public interface UserCouponRep extends JpaRepository<UserCoupon, String> {
    /**
     * 分页列出积分记录
     *
     * @param userId {String}
     * @param pageable {Pageable}
     * @return Page<User>
     */
    Page<UserCoupon> findByUser_IdOrderByCreateTimeDesc(String userId, Pageable pageable);
}
