package com.waiterlong.vipmis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/9/29
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Entity
@Table(name = "pay_log")
@Data
public class PayLog {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "pay_bill")
    private Long payBill;

    @Column(name = "pay_date")
    private Date payDate;

    @Column(name = "pay_desc")
    private Date payDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayLog payLog = (PayLog) o;
        return Objects.equals(id, payLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
