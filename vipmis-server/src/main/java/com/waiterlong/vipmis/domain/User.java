package com.waiterlong.vipmis.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description: 用户实体
 * \
 *
 * @author waiterlong
 */
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Column(name="real_name")
    private String realName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "wechat_name")
    private String weChatName;

    @Column(name = "wechat_sex")
    private String weChatSex;

    @Column(name = "wechat_openid")
    private String weChatOpenId;

    @Column(name = "wechat_img_url")
    private String weChatImgUrl;

    @Column(name = "register_time")
    private Date registerTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "goal")
    private Long goal;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<GoalLog> goalLogs = Lists.newLinkedList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<UserCoupon> userCoupons = Lists.newLinkedList();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
