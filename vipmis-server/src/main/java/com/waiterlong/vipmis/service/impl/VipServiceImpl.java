package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.domain.DepositLog;
import com.waiterlong.vipmis.domain.GoalLog;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.wxvo.BalanceLogVo;
import com.waiterlong.vipmis.domain.wxvo.IntegralLogVo;
import com.waiterlong.vipmis.domain.wxvo.VipVo;
import com.waiterlong.vipmis.repository.DepositLogRep;
import com.waiterlong.vipmis.repository.GoalLogRep;
import com.waiterlong.vipmis.repository.UserCouponRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.IVipService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

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

    @Resource(name="depositLogRep")
    private DepositLogRep depositLogRep;

    @Resource(name="goalLogRep")
    private GoalLogRep goalLogRep;

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

    @Override
    public Object updateVipInfo(@NotNull VipVo vipVo) {
        if (null == vipVo.getId() || vipVo.getId().trim().isEmpty()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(vipVo.getId().trim());
        if (!userOptional.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        User user = userOptional.get();
        if (null != vipVo.getBirthday()) {
            user.setBirthday(vipVo.getBirthday());
        }
        if (null != vipVo.getName()) {
            user.setRealName(vipVo.getName());
        }
        if (null != vipVo.getSex()) {
            user.setWeChatSex(vipVo.getSex());
        }
        if (null != vipVo.getPhone()) {
            user.setPhone(vipVo.getPhone());
        }
        if (null != vipVo.getIdCard()) {
            user.setIdCard(vipVo.getIdCard());
        }
        userRep.save(user);
        return ResponseUtil.ok();
    }

    @Override
    public Object listDepositLogByPage(@NotNull Map<String, Object> paramMap, @NotNull Pageable pageable) {
        Page<DepositLog> depositLogPage = depositLogRep.findByUser_IdOrderByCreateTimeDesc((String)paramMap.get("userId"), pageable);
        return ResponseUtil.ok(PageResult.setPageResult(pageable, depositLogPage.getTotalElements(), BalanceLogVo.convertDepositLog(depositLogPage.getContent())));
    }

    @Override
    public Object listGoalLogByPage(@NotNull Map<String, Object> paramMap, @NotNull Pageable pageable) {
        Page<GoalLog> goalLogPage = goalLogRep.findByUser_IdOrderByCreateTimeDesc((String)paramMap.get("userId"), pageable);
        return ResponseUtil.ok(PageResult.setPageResult(pageable, goalLogPage.getTotalElements(), IntegralLogVo.convertGoalLog(goalLogPage.getContent())));
    }
}
