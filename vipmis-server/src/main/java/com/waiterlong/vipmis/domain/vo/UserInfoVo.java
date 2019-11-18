package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.DateUtil;
import com.waiterlong.vipmis.utils.PinyinUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-15 08:51
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Data
public class UserInfoVo {
    private String id;

    private String realName;

    private String phone;

    private String cardCord;

    private String idCard;

    private String weChatName;

    private String weChatSex;

    private String weChatOpenId;

    private String weChatImgUrl;

    private Date registerTime;

    private String registerTimeStr;

    private Date lastLoginTime;

    private String lastLoginTimeStr;

    private String firstLetter;

    private String cardType;

    private Long goal;

    private Float deposit;

    private LabelVo labelVo;

    private Float catSell;

    private Float washProtectService;

    private Float peripheralProducts;

    public static List<UserInfoVo> convertUser(List<User> users){
        List<UserInfoVo> userVos = Lists.newArrayList();
        for (User user : users) {
            userVos.add(convertUser(user));
        }
        return userVos;
    }

    public static UserInfoVo convertUser(User user) {
        if (null == user) {
            return null;
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        AbstractMyBeanUtils.copyProperties(user, userInfoVo);
        userInfoVo.setFirstLetter(PinyinUtil.getFirstLetter(userInfoVo.realName));
        userInfoVo.setLabelVo(LabelVo.convertLabel(user.getLabel()));
        userInfoVo.setLastLoginTimeStr(DateUtil.dateToString(userInfoVo.getLastLoginTime()));
        userInfoVo.setRegisterTimeStr(DateUtil.dateToString(userInfoVo.getRegisterTime()));
        return userInfoVo;
    }
}
