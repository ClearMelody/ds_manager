package com.waiterlong.vipmis.service.impl;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.DepositLog;
import com.waiterlong.vipmis.domain.GoalLog;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.text.spi.DecimalFormatSymbolsProvider;
import java.util.Date;
import java.util.List;
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
    Logger logger = LoggerFactory.getLogger(VipServiceImpl.class);

    @Resource(name="userRep")
    private UserRep userRep;

    @Resource(name="userCouponRep")
    private UserCouponRep userCouponRep;

    @Resource(name="depositLogRep")
    private DepositLogRep depositLogRep;

    @Resource(name="goalLogRep")
    private GoalLogRep goalLogRep;

    @Override
    public Result findVipByOpenId(String openid) {
        logger.debug("findVipByOpenId openid: {}", openid);

        User user = userRep.findByWeChatOpenId(openid);
        if (null == user) {
            return Result.fail(Constant.REQUEST_FAIL_CODE, "用户不存在");
        }
        user.setLastLoginTime(new Date());
        user = userRep.save(user);
        VipVo vipVo = VipVo.convertUser(user);
        vipVo.setCoupon(userCouponRep.countByUser(user));
        return Result.ok(vipVo);
    }

    @Override
    public Result updateVipInfo(@NotNull VipVo vipVo) {
        logger.debug("updateVipInfo vipVo: {}", vipVo);

        if (null == vipVo.getId() || vipVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(vipVo.getId().trim());
        if (!userOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        User user = userOptional.get();
        if (null != vipVo.getAvatarUrl()) {
            user.setWeChatName(vipVo.getAvatarUrl());
        }
        if (null != vipVo.getBirthday()) {
            user.setBirthday(vipVo.getBirthday());
        }
        if (null != vipVo.getName()) {
            user.setRealName(vipVo.getName());
        }
        if (null != vipVo.getVipName()) {
            user.setWeChatName(vipVo.getVipName());
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
        return Result.ok();
    }

    @Override
    public Result listDepositLogByPage(@NotNull Map<String, Object> paramMap, @NotNull Pageable pageable) {
        logger.debug("listDepositLogByPage paramMap: {}", paramMap);
        logger.debug("listDepositLogByPage pageable: {}", pageable);

        Page<DepositLog> depositLogPage = depositLogRep.findByUser_IdOrderByCreateTimeDesc((String)paramMap.get("userId"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, depositLogPage.getTotalElements(), BalanceLogVo.convertDepositLog(depositLogPage.getContent())));
    }

    @Override
    public Result listGoalLogByPage(@NotNull Map<String, Object> paramMap, @NotNull Pageable pageable) {
        logger.debug("listGoalLogByPage paramMap: {}", paramMap);
        logger.debug("listGoalLogByPage pageable: {}", pageable);

        Page<GoalLog> goalLogPage = goalLogRep.findByUser_IdOrderByCreateTimeDesc((String)paramMap.get("userId"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, goalLogPage.getTotalElements(), IntegralLogVo.convertGoalLog(goalLogPage.getContent())));
    }

    @Override
    public Result listVipsByPage(Map<String, Object> paramMap, Pageable pageable) {
        logger.debug("listVipsByPage paramMap: {}", paramMap);
        logger.debug("listVipsByPage pageable: {}", pageable);

        Page<User> userPage = userRep.findByRealNameIsContainingOrderByRegisterTimeDesc((String)paramMap.get("realName"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, userPage.getTotalElements(), UserInfoVo.convertUser(userPage.getContent())));
    }

    @Override
    public Result addVipInfo(@NotNull VipVo vipVo) {
        logger.debug("addVipInfo vipVo: {}", vipVo);

        if (null == vipVo.getOpenid() || vipVo.getOpenid().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        User tUser = userRep.findByWeChatOpenId(vipVo.getOpenid());
        if (null != tUser) {
            return Result.fail(1, "该用户已存在");
        }
        User user = new User();
        user.setIdCard(vipVo.getIdCard());
        user.setPhone(vipVo.getPhone());
        user.setWeChatSex(vipVo.getSex());
        user.setRealName(vipVo.getName());
        user.setWeChatName(vipVo.getVipName());
        user.setBirthday(vipVo.getBirthday());
        user.setWeChatImgUrl(vipVo.getAvatarUrl());
        user.setGoal(0L);
        user.setDeposit(0F);
        user.setCardType("普通VIP");
        user.setRegisterTime(new Date());
        //TODO
        user.setCardCord("");
        user.setWeChatOpenId(vipVo.getOpenid());
        user = userRep.save(user);
        return Result.ok(VipVo.convertUser(user));
    }

    @Override
    public Result charge(@NotNull UserInfoVo userInfoVo) {
        logger.debug("charge userInfoVo: {}", userInfoVo);

        if (null == userInfoVo.getId() || userInfoVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(userInfoVo.getId());
        if (!userOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        User user = userOptional.get();
        user.setDeposit(Float.sum(user.getDeposit(), userInfoVo.getDeposit()));
        user = userRep.save(user);

        DepositLog depositLog = new DepositLog();
        depositLog.setUser(user);
        depositLog.setCreateTime(new Date());
        depositLog.setTitle("充值");
        depositLog.setValue(userInfoVo.getDeposit());
        depositLogRep.save(depositLog);

        return Result.ok();
    }

    @Override
    @Transactional
    public Result pay(@NotNull UserInfoVo userInfoVo) {
        logger.debug("pay userInfoVo: {}", userInfoVo);

        if (null == userInfoVo.getId() || userInfoVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(userInfoVo.getId());
        if (!userOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        User user = userOptional.get();
        Float deposit = user.getDeposit();
        Long goal = user.getGoal();
        List<GoalLog> goalLogs = Lists.newLinkedList();
        List<DepositLog> depositLogs = Lists.newLinkedList();
        Date nowDate = new Date();
        if (0F != userInfoVo.getCatSell()) {
            deposit = deposit - userInfoVo.getCatSell();
            Float t = userInfoVo.getCatSell() * 100;
            goal += t.longValue();
            String title = "猫咪售卖";

            goalLogs.add(makeGoalLog(t.longValue(), title, nowDate, user));

            depositLogs.add(makeDepositLog(userInfoVo.getCatSell(), title, nowDate, user));
        }
        if (0F != userInfoVo.getPeripheralProducts()) {
            deposit = deposit - userInfoVo.getPeripheralProducts();
            Float t = userInfoVo.getPeripheralProducts() * 100;
            goal += t.longValue();
            String title = "周边产品";

            goalLogs.add(makeGoalLog(t.longValue(), title, nowDate, user));

            depositLogs.add(makeDepositLog(userInfoVo.getPeripheralProducts(), title, nowDate, user));
        }
        if (0F != userInfoVo.getWashProtectService()) {
            deposit = deposit - userInfoVo.getWashProtectService();
            Float t = userInfoVo.getWashProtectService() * 100;
            goal += t.longValue();
            String title = "洗护服务";

            goalLogs.add(makeGoalLog(t.longValue(), title, nowDate, user));

            depositLogs.add(makeDepositLog(userInfoVo.getWashProtectService(), title, nowDate, user));
        }
        user.setDeposit(deposit);
        user.setGoal(goal);
        user = userRep.save(user);

        goalLogRep.saveAll(goalLogs);
        depositLogRep.saveAll(depositLogs);

        return Result.ok(UserInfoVo.convertUser(user));
    }

    @Override
    @Transactional
    public Result deleteUser(@NotNull String userId) {
        logger.debug("deleteUser userId: {}", userId);

        if (userId.trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(userId);
        if (!userOptional.isPresent()) {
            return Result.fail(Constant.REQUEST_FAIL_CODE, "用户不存在");
        }
        List<GoalLog> goalLogs = goalLogRep.findByUser_Id(userId);
        goalLogRep.deleteAll(goalLogs);
        List<DepositLog> depositLogs = depositLogRep.findByUser_Id(userId);
        depositLogRep.deleteAll(depositLogs);
        userRep.delete(userOptional.get());
        return Result.ok();
    }

    private DepositLog makeDepositLog(Float goalChange, String title, Date createTime, User user) {
        DepositLog depositLog = new DepositLog();
        depositLog.setValue(-goalChange);
        depositLog.setTitle(title);
        depositLog.setCreateTime(createTime);
        depositLog.setUser(user);
        return depositLog;
    }

    private GoalLog makeGoalLog(Long goalChange, String title, Date createTime, User user) {
        GoalLog goalLog = new GoalLog();
        goalLog.setGoalChange(goalChange);
        goalLog.setUser(user);
        goalLog.setCreateTime(createTime);
        goalLog.setTitle(title);
        return goalLog;
    }
}
