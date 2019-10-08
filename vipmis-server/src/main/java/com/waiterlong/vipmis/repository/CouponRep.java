package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.Coupon;
import com.waiterlong.vipmis.domain.User;
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
@Repository("couponRep")
public interface CouponRep extends JpaRepository<Coupon, String> {
    /**
     * 分页列出优惠券
     *
     * @param pageable {Pageable}
     * @return Page<Coupon>
     */
    Page<Coupon> findByOrderByCreateTimeDesc(Pageable pageable);

}
