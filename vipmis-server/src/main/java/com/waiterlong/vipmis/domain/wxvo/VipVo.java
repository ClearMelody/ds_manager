package com.waiterlong.vipmis.domain.wxvo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.User;
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
    private String vipName;

    private String name;

    private String phone;

    private String cardCord;

    private String balance;

    private String integral;

    private Integer coupon;

    public static VipVo convertUser(User user) {
        if (null == user) {
            return null;
        }
        VipVo vipVo = new VipVo();
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
