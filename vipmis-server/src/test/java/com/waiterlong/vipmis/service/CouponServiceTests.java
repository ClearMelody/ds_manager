package com.waiterlong.vipmis.service;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.assets.WeiXinApiUrl;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.Coupon;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.CouponVo;
import com.waiterlong.vipmis.domain.vo.UserCouponVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.repository.CouponRep;
import com.waiterlong.vipmis.repository.UserRep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTests {
    @Resource(name = "iCouponService")
    private ICouponService couponService;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Resource(name = "couponRep")
    private CouponRep couponRep;

    @Test
    public void listCouponByPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, Object> paramMap = Maps.newHashMap();
        Map result = (Map)couponService.listCouponByPage(paramMap, pageable);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void listUserCouponByPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", Constant.TEST_USER_ID);
        Map result = (Map)couponService.listUserCouponByPage(paramMap, pageable);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void addCoupon() {
        CouponVo couponVo = new CouponVo();
        couponVo.setDescription("测试的优惠券行不行");
        couponVo.setTitle("测试优惠券");
        couponVo.setIndate(new Date());
        Map result = (Map)couponService.addCoupon(couponVo);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void updateCoupon() {
        CouponVo couponVo = new CouponVo();
        couponVo.setDescription("测试的优惠券行不行修改");
        couponVo.setTitle("测试优惠券修改");
        couponVo.setIndate(new Date());
        couponVo.setId(Constant.TEST_COUPON_ID);
        Map result = (Map)couponService.updateCoupon(couponVo);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void addUserCoupon() {
        UserCouponVo userCouponVo = new UserCouponVo();
        Optional<User> userOptional = userRep.findById(Constant.TEST_USER_ID);
        Optional<Coupon> couponOptional = couponRep.findById(Constant.TEST_COUPON_ID);
        if (!userOptional.isPresent() || !couponOptional.isPresent()) {
            System.out.println("执行因某个ID找不到而中断");
            return;
        }
        userCouponVo.setUserInfoVo(UserInfoVo.convertUser(userOptional.get()));
        userCouponVo.setCouponVo(CouponVo.convertCoupon(couponOptional.get()));
        Map result = (Map)couponService.addUserCoupon(userCouponVo);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void useUserCoupon() {
        Map result = (Map)couponService.useUserCoupon(Constant.TEST_USER_COUPON_ID);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }
}
