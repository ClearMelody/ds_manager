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
    private String realName = "";

    @Column(name = "phone")
    private String phone = "";

    @Column(name = "wechat_name")
    private String weChatName = "";

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

    @Column(name = "card_cord")
    private String cardCord = "";

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "id_card")
    private String idCard = "";

    /**
     * 会员卡类型：普通VIP，超级VIP
     */
    @Column(name = "card_type")
    private String cardType;

    @Column(name = "goal")
    private Long goal;

    @Column(name = "deposit")
    private Float deposit;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<GoalLog> goalLogs = Lists.newLinkedList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<UserCoupon> userCoupons = Lists.newLinkedList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<DepositLog> depositLogs = Lists.newLinkedList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<PayLog> payLogs = Lists.newLinkedList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Cat> cats = Lists.newLinkedList();

    @ManyToOne(fetch = FetchType.LAZY)
    private Label label;

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
