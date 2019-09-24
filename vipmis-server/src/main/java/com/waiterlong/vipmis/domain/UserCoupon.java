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
 * \* Description: 用户优惠券实体
 * \
 *
 * @author waiterlong
 */
@Entity
@Table(name = "user_coupon")
@Data
public class UserCoupon {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "used_time")
    private Date usedTime;

    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCoupon that = (UserCoupon) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
