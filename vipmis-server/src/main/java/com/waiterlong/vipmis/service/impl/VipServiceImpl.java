package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.wxvo.VipVo;
import com.waiterlong.vipmis.repository.UserCouponRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.IVipService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Service("iVipService")
public class VipServiceImpl extends BaseServiceImpl implements IVipService {
    @Resource(name="userRep")
    private UserRep userRep;

    @Resource(name="userCouponRep")
    private UserCouponRep userCouponRep;

    @Override
    public Object findVipByOpenId(String openid) {
        User user = userRep.findByWeChatOpenId(openid);
        if (null == user) {
            return ResponseUtil.badArgumentValue();
        }
        VipVo vipVo = VipVo.convertUser(user);
        vipVo.setCoupon(userCouponRep.countByUser(user));
        return ResponseUtil.ok(vipVo);
    }
}
