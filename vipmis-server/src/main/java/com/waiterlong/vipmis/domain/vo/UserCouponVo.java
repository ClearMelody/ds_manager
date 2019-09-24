package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.UserCoupon;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-15 08:51
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Data
public class UserCouponVo {
    private String id;

    private Date usedTime;

    private Date createTime;

    private UserInfoVo userInfoVo;

    private CouponVo couponVo;

    public static List<UserCouponVo> convertUserCoupon(List<UserCoupon> userCoupons){
        List<UserCouponVo> userCouponVos = Lists.newArrayList();
        for (UserCoupon userCoupon : userCoupons) {
            userCouponVos.add(convertUserCoupon(userCoupon));
        }
        return userCouponVos;
    }

    public static UserCouponVo convertUserCoupon(UserCoupon userCoupon) {
        if (null == userCoupon) {
            return null;
        }
        UserCouponVo userCouponVo = new UserCouponVo();
        AbstractMyBeanUtils.copyProperties(userCoupon, userCouponVo);
        userCouponVo.setCouponVo(CouponVo.convertCoupon(userCoupon.getCoupon()));
        userCouponVo.setUserInfoVo(UserInfoVo.convertUser(userCoupon.getUser()));
        return userCouponVo;
    }
}
