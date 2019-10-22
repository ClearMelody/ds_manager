package com.waiterlong.vipmis.domain.wxvo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.GoalLog;
import com.waiterlong.vipmis.domain.UserCoupon;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.DateUtil;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class VipCouponVo {
    private String label;

    private String money;

    private String time;

    private String round;

    private String info;

    public static VipCouponVo convertUserCoupon(UserCoupon userCoupon) {
        if (null == userCoupon) {
            return null;
        }
        VipCouponVo vipCouponVo = new VipCouponVo();
        AbstractMyBeanUtils.copyProperties(userCoupon, vipCouponVo);
        vipCouponVo.setTime(DateUtil.dateToString(userCoupon.getCreateTime(), DateUtil.FMT_YYYY_MM_DD));
        return vipCouponVo;
    }
    public static List<VipCouponVo> convertUserCoupon(List<UserCoupon> userCoupons){
        List<VipCouponVo> vipCouponVos = Lists.newArrayList();
        for (UserCoupon userCoupon : userCoupons) {
            vipCouponVos.add(convertUserCoupon(userCoupon));
        }
        return vipCouponVos;
    }
}
