package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.domain.vo.CouponVo;
import com.waiterlong.vipmis.domain.vo.UserCouponVo;
import com.waiterlong.vipmis.service.base.IBaseService;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
public interface ICouponService extends IBaseService {
    /**
     * 分页列出优惠券
     * @param paramMap {Map<String, Object>}
     * @param pageable {Pageable}
     * @return Object
     */
    Object listCouponByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 分页列出用户有的优惠券
     * @param paramMap {Map<String, Object>}
     * @param pageable {Pageable}
     * @return Object
     */
    Object listUserCouponByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 添加优惠券
     * @param couponVo {CouponVo}
     * @return Object
     */
    Object addCoupon(CouponVo couponVo);

    /**
     * 更新优惠券
     * @param couponVo {CouponVo}
     * @return Object
     */
    Object updateCoupon(CouponVo couponVo);

    /**
     * 添加用户的优惠券
     * @param userCouponVo {UserCouponVo}
     * @return Object
     */
    Object addUserCoupon(UserCouponVo userCouponVo);

    /**
     * 使用用户的优惠券
     * @param userCouponId {String}
     * @return Object
     */
    Object useUserCoupon(String userCouponId);
}
