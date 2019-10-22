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
 * \* Description: 优惠券实体
 * \
 *
 * @author waiterlong
 */
@Entity
@Table(name = "coupon")
@Data
public class Coupon {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Column(name="title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "valid_day")
    private Integer validDay;

    @Column(name = "min_pay")
    private String minPay;

    @Column(name = "create_time")
    private Date createTime;

    @OneToMany(mappedBy = "coupon", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<UserCoupon> userCoupons = Lists.newLinkedList();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
