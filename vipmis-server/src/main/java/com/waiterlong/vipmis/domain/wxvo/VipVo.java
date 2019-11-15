package com.waiterlong.vipmis.domain.wxvo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
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
public class VipVo {
    private String id;

    private String vipName;

    private String name;

    private String openid;

    private String phone;

    private String avatarUrl;

    private String cardCord;

    private String cardType;

    /**
     * 余额
     */
    private Float balance;

    /**
     * 积分
     */
    private Long integral;

    private String idCard;

    private String birthday;

    private String sex;

    private Integer coupon;

    public static VipVo convertUser(User user) {
        if (null == user) {
            return null;
        }
        VipVo vipVo = new VipVo();
        AbstractMyBeanUtils.copyProperties(user, vipVo);
        vipVo.setBalance(user.getDeposit());
        vipVo.setVipName(user.getWeChatName());
        vipVo.setName(user.getRealName());
        vipVo.setIntegral(user.getGoal());
        vipVo.setSex(user.getWeChatSex());
        vipVo.setAvatarUrl(user.getWeChatImgUrl());
        return vipVo;
    }

    public static List<VipVo> convertUser(List<User> users){
        List<VipVo> vipVos = Lists.newArrayList();
        for (User user : users) {
            vipVos.add(convertUser(user));
        }
        return vipVos;
    }
}
