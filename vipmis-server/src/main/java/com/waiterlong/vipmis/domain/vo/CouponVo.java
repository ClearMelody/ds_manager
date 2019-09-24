package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.Coupon;
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
public class CouponVo {
    private String id;

    private String title;

    private String description;

    private Date indate;

    private Date createTime;

    private List<UserCouponVo> userCouponVos;

    public static List<CouponVo> convertCoupon(List<Coupon> coupons){
        List<CouponVo> couponVos = Lists.newArrayList();
        for (Coupon coupon : coupons) {
            couponVos.add(convertCoupon(coupon));
        }
        return couponVos;
    }

    public static CouponVo convertCoupon(Coupon coupon) {
        if (null == coupon) {
            return null;
        }
        CouponVo couponVo = new CouponVo();
        AbstractMyBeanUtils.copyProperties(coupon, couponVo);
        return couponVo;
    }
}
