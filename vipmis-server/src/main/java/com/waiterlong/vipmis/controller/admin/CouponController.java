package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.CouponVo;
import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.domain.vo.UserCouponVo;
import com.waiterlong.vipmis.service.ICouponService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RestController
@RequestMapping("/api/admin")
public class CouponController {
    @Resource(name = "iCouponService")
    private ICouponService couponService;

    @RequestMapping(value = "/coupon/list", method = RequestMethod.GET)
    public Object listCouponByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        return couponService.listCouponByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/coupon/add", method = RequestMethod.POST)
    public Object addCoupon(@RequestBody CouponVo couponVo){
        return  couponService.addCoupon(couponVo);
    }

    @RequestMapping(value = "/coupon/update", method = RequestMethod.POST)
    public Object updateCoupon(@RequestBody CouponVo couponVo){
        return  couponService.updateCoupon(couponVo);
    }

    @RequestMapping(value = "/user/coupon/list", method = RequestMethod.GET)
    public Object listUserCouponByPage(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return couponService.listUserCouponByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/user/coupon/add", method = RequestMethod.POST)
    public Object addUserCoupon(@RequestBody UserCouponVo userCouponVo) {
        return couponService.addUserCoupon(userCouponVo);
    }

    @RequestMapping(value = "/user/coupon/use", method = RequestMethod.GET)
    public Object updateUserCoupon(@RequestParam String userCouponId) {
        return couponService.useUserCoupon(userCouponId);
    }
}
