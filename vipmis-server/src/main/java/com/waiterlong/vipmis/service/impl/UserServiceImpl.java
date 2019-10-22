package com.waiterlong.vipmis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.GoalLogVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.IUserService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * \* Description: 积分实体
 * \
 *
 * @author waiterlong
 */
@Service("iUserService")
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "userRep")
    private UserRep userRep;

    @Override
    public Object findUserInfoById(@NotNull  String id) {
        if (id.isEmpty()) {
            return ResponseUtil.badArgumentValue();
        }
        Optional<User> userOpt = userRep.findById(id);
        if (!userOpt.isPresent()) {
            return ResponseUtil.badArgumentValue();
        }
        UserInfoVo userInfoVo = UserInfoVo.convertUser(userOpt.get());
        return ResponseUtil.ok(userInfoVo);
    }

    @Override
    public Object addUser(@NotNull UserVo userVo) {
        if (null != userVo.getId()) {
            return ResponseUtil.badArgumentValue();
        }
        User user = new User();
        AbstractMyBeanUtils.copyProperties(userVo, user);
        user.setLastLoginTime(new Date());
        user.setRegisterTime(new Date());
        user.setGoal(0L);

        user = userRep.save(user);
        return ResponseUtil.ok(UserInfoVo.convertUser(user));
    }

    @Override
    public Object listUsersByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<User> userPage = userRep.findByRealNameIsContainingOrderByRegisterTimeDesc((String)paramMap.get("realName"), pageable);
        return ResponseUtil.ok(PageResult.setPageResult(pageable, userPage.getTotalElements(), UserInfoVo.convertUser(userPage.getContent())));
    }
}
