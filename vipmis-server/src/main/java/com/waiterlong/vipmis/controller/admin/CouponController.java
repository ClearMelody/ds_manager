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
    private ICouponService iCouponService;

    @RequestMapping(value = "/coupon/list", method = RequestMethod.GET)
    public Result listCouponByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        return iCouponService.listCouponByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/coupon/add", method = RequestMethod.POST)
    public Result addCoupon(@RequestBody CouponVo couponVo){
        return  iCouponService.addCoupon(couponVo);
    }

    @RequestMapping(value = "/coupon/update", method = RequestMethod.POST)
    public Result updateCoupon(@RequestBody CouponVo couponVo){
        return  iCouponService.updateCoupon(couponVo);
    }

    @RequestMapping(value = "/user/coupon/list", method = RequestMethod.GET)
    public Result listUserCouponByPage(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return iCouponService.listUserCouponByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/user/coupon/add", method = RequestMethod.POST)
    public Result addUserCoupon(@RequestBody UserCouponVo userCouponVo) {
        return iCouponService.addUserCoupon(userCouponVo);
    }

    @RequestMapping(value = "/user/coupon/use", method = RequestMethod.GET)
    public Object updateUserCoupon(@RequestParam String userCouponId) {
        return iCouponService.useUserCoupon(userCouponId);
    }
}
