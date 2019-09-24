package com.waiterlong.vipmis.domain.vo;

import lombok.Data;

import java.util.Date;

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
public class UserVo {
    private String id;

    private String realName;

    private String phone;

    private String password;

    private String weChatName;

    private String weChatSex;

    private String weChatOpenId;

    private String weChatImgUrl;

    private Date registerTime;

    private Date lastLoginTime;

}
