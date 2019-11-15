package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
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
     * @return Result
     */
    Result listCouponByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 分页列出用户有的优惠券
     * @param paramMap {Map<String, Object>}
     * @param pageable {Pageable}
     * @return Result
     */
    Result listUserCouponByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 添加优惠券
     * @param couponVo {CouponVo}
     * @return Result
     */
    Result addCoupon(CouponVo couponVo);

    /**
     * 更新优惠券
     * @param couponVo {CouponVo}
     * @return Result
     */
    Result updateCoupon(CouponVo couponVo);

    /**
     * 添加用户的优惠券
     * @param userCouponVo {UserCouponVo}
     * @return Result
     */
    Result addUserCoupon(UserCouponVo userCouponVo);

    /**
     * 使用用户的优惠券
     * @param userCouponId {String}
     * @return Result
     */
    Result useUserCoupon(String userCouponId);
}
