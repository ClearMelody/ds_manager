package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.domain.Coupon;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.UserCoupon;
import com.waiterlong.vipmis.domain.vo.CouponVo;
import com.waiterlong.vipmis.domain.vo.UserCouponVo;
import com.waiterlong.vipmis.repository.CouponRep;
import com.waiterlong.vipmis.repository.UserCouponRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.ICouponService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Service("iCouponService")
public class CouponServiceImpl extends BaseServiceImpl implements ICouponService {
    @Resource(name = "couponRep")
    private CouponRep couponRep;

    @Resource(name = "userCouponRep")
    private UserCouponRep userCouponRep;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Override
    public Object listCouponByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<Coupon> couponPage = couponRep.findByOrderByCreateTimeDesc(pageable);
        return ResponseUtil.ok(PageResult.setPageResult(pageable, couponPage.getTotalElements(), CouponVo.convertCoupon(couponPage.getContent())));
    }

    @Override
    public Object listUserCouponByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<UserCoupon> userCouponPage = userCouponRep.findByUser_IdOrderByCreateTimeDesc((String)paramMap.get("userId"), pageable);
        return ResponseUtil.ok(PageResult.setPageResult(pageable, userCouponPage.getTotalElements(), UserCouponVo.convertUserCoupon(userCouponPage.getContent())));
    }

    @Override
    public Object addCoupon(@NotNull CouponVo couponVo) {
        if (null != couponVo.getId()) {
            return ResponseUtil.badArgumentValue();
        }
        Coupon coupon = new Coupon();
        AbstractMyBeanUtils.copyProperties(couponVo, coupon);
        coupon.setCreateTime(new Date());
        coupon = couponRep.save(coupon);
        return ResponseUtil.ok(CouponVo.convertCoupon(coupon));
    }

    @Override
    public Object updateCoupon(@NotNull CouponVo couponVo) {
        if (null == couponVo.getId() || couponVo.getId().isEmpty()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<Coupon> couponOptional = couponRep.findById(couponVo.getId());
        if (!couponOptional.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        Coupon coupon = new Coupon();
        AbstractMyBeanUtils.copyProperties(couponVo, coupon);
        coupon.setCreateTime(couponOptional.get().getCreateTime());
        coupon = couponRep.save(coupon);
        return ResponseUtil.ok(CouponVo.convertCoupon(coupon));
    }

    @Override
    public Object addUserCoupon(@NotNull UserCouponVo userCouponVo) {
        if (null != userCouponVo.getId()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<Coupon> couponOptional = couponRep.findById(userCouponVo.getCouponVo().getId());
        if (!couponOptional.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(userCouponVo.getUserInfoVo().getId());
        if (!userOptional.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        UserCoupon userCoupon = new UserCoupon();
        AbstractMyBeanUtils.copyProperties(userCouponVo, userCoupon);
        userCoupon.setUser(userOptional.get());
        userCoupon.setCoupon(couponOptional.get());
        userCoupon.setCreateTime(new Date());
        userCoupon = userCouponRep.save(userCoupon);
        return ResponseUtil.ok(UserCouponVo.convertUserCoupon(userCoupon));
    }

    @Override
    public Object useUserCoupon(@NotNull String userCouponId) {
        if (userCouponId.isEmpty()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<UserCoupon> userCouponOptional = userCouponRep.findById(userCouponId);
        if (!userCouponOptional.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        UserCoupon userCoupon = userCouponOptional.get();
        userCoupon.setUsedTime(new Date());
        userCoupon = userCouponRep.save(userCoupon);
        return ResponseUtil.ok(UserCouponVo.convertUserCoupon(userCoupon));
    }
}
